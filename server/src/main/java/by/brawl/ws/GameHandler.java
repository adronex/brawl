package by.brawl.ws;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.service.AccountService;
import by.brawl.service.SecurityService;
import by.brawl.ws.dto.*;
import by.brawl.ws.pojo.GameState;
import by.brawl.ws.pojo.PlayerStateHolder;
import by.brawl.ws.service.MatchmakingService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// todo: Noisia - Machine Gun
// todo: Noisia - Hunter Theme

// todo: matchmaking service
// todo: game service
// steps:
// 1. choose squad.
// OPEN WEBSOCKET.
// 2. send it's id in mm service
// 3. mm service create room with 2 players and send pre-game squads DTOs.
// MULLIGAN STATE
// 4. player send heroes and positions (order matters).
// 5. waiting for second player if necessary.
// 6. send queue, enemy positions.
// PLAYING STATE
// 7. send valid spell id and positions array ({self: [], enemy: [0, 3]}.
// 8. game service validates input data.
// 9. set triggered spells as exposed.
// 10. send updated queue, heroes through self/enemy parser.
@Component
public class GameHandler extends TextWebSocketHandler {

    private MatchmakingService matchmakingService;

    private Map<String, PlayerStateHolder> playerStates = new HashMap<>();
    private GameState gameState = GameState.NOT_STARTED;
    private Queue<Hero> heroesQueue = new LinkedList<>();
    private Map<String, List<HeroDto>> heroes = new HashMap<>();
    private List<String> history = new ArrayList<>();

    @Autowired
    public GameHandler(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {

       // Account account = accountService.findByEmail(session.getPrincipal().getName());

        playerStates.put(session.getId(), new PlayerStateHolder(null, session, false));

        sendInfoMessage(session, "Connection established - " + null);

        if (playerStates.size() == 2) {
            gameState = GameState.MULLIGAN;
            sendInfoMessageToAll("Mulligan state");
        } else {
            sendInfoMessage(session, "Finding opponent");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        JSONObject request = new JSONObject(message.getPayload());
        ClientRequestType type = request.getEnum(ClientRequestType.class, "type");
        if (ClientRequestType.INITIAL.equals(type)) {
            JSONObject body = request.getJSONObject("body");
            String squadId = body.getString("squadId");
            matchmakingService.addInPool(session, squadId);
        }

        if (GameState.MULLIGAN.equals(gameState)) {
            playerStates.get(session.getId()).setReadyForGame(true);
            if (playerStates.values()
                    .stream()
                    .filter(s -> !s.getReadyForGame())
                    .count() == 0) {
                sendInfoMessageToAll("Game started!");
                gameState = GameState.PLAYING;
                setQueue();
                playerStates.values().forEach(ps -> ps.getSpells().addAll(getSpells()));
                sendGameTurnToAll();
            } else {
                sendInfoMessage(session, "Opponent is still choosing");
            }
        } else if (GameState.PLAYING.equals(gameState)) {
            Hero currentHero = heroesQueue.element();
            if (!playerStates.get(session.getId())
                    .getPlayer()
                    .equals(currentHero.getOwner())) {
                sendInfoMessage(session, "Not your turn!");
                return;
            }
            // todo: check for spell is missing
            currentHero.hit(15);
            heroesQueue.remove();
            heroesQueue.add(currentHero);
            checkQueue();
            checkGameIsFinished();
            sendGameTurnToAll();
            if (GameState.END.equals(gameState)) {
                sendInfoMessageToAll("Game over!");
                clearSessions();
            }
        }
    }

    private void sendInfoMessage(WebSocketSession session, String text) {
        sendDto(session, new MessageDto(text));
    }

    private void sendInfoMessageToAll(String text) {
        playerStates.values()
                .stream()
                .map(PlayerStateHolder::getSession)
                .forEach(s -> sendInfoMessage(s, text));
    }

    private void sendDto(WebSocketSession session, JsonDto dto) {
        try {
            session.sendMessage(new TextMessage(dto.asJson()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private void sendGameTurn(WebSocketSession session) {
        Account receiver = playerStates.get(session.getId()).getPlayer();
        sendDto(session, new GameTurnDto(gameState, heroesQueue, receiver));
    }

    @Deprecated
    private void sendGameTurnToAll() {
        playerStates.values()
                .stream()
                .map(PlayerStateHolder::getSession)
                .forEach(this::sendGameTurn);
    }

    private void setQueue() {
        playerStates.values()
                .stream()
                .map(PlayerStateHolder::getPlayer)
                .forEach(a -> heroesQueue.addAll(a.getSquads().iterator().next().getHeroes()));
    }

    private Set<SpellDto> getSpells() {
        Set<SpellDto> spells = new HashSet<>();
        heroesQueue.forEach(h ->
                spells.addAll(h.getSpells()
                        .stream()
                        .map(s -> new SpellDto(s.getId(), h.getId()))
                        .collect(Collectors.toList())));
        return spells;
    }

    private void checkQueue() {
        List<Hero> aliveHeroes = heroesQueue.stream().filter(Hero::isAlive).collect(Collectors.toList());
        Queue<Hero> updatedQueue = new LinkedList<>();
        updatedQueue.addAll(aliveHeroes);
        heroesQueue.clear();
        heroesQueue.addAll(updatedQueue);
    }

    private void checkGameIsFinished() {
        Map<Account, List<Hero>> splitList = heroesQueue.stream().collect(Collectors.groupingBy(Hero::getOwner));

        playerStates.values()
                .forEach(s -> {
                    Boolean alive = splitList.get(s.getPlayer()) != null;
                    s.setAlive(alive);
                });

        if (playerStates.values()
                .stream()
                .filter(s -> !s.getAlive()).count() > 0) {
            gameState = GameState.END;
        }
    }

    private void clearSessions() {
        playerStates.values()
                .stream()
                .map(PlayerStateHolder::getSession)
                .forEach(s -> {
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        playerStates.clear();
        heroesQueue.clear();
    }
}
