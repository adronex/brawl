package by.brawl.ws;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.service.AccountService;
import by.brawl.ws.dto.GameTurnDto;
import by.brawl.ws.dto.JsonDto;
import by.brawl.ws.dto.MessageDto;
import by.brawl.ws.pojo.GameState;
import by.brawl.ws.pojo.PlayerStateHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GameHandler extends TextWebSocketHandler {

    private AccountService accountService;

    private Map<String, PlayerStateHolder> playerStates = new HashMap<>();
    private GameState gameState = GameState.NOT_STARTED;
    private Queue<Hero> heroesQueue = new LinkedList<>();
    private List<String> history = new ArrayList<>();

    @Autowired
    public GameHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {

        Account account = accountService.findByEmail(session.getPrincipal().getName());

        playerStates.put(session.getId(), new PlayerStateHolder(account, session, false));

        sendInfoMessage(session, "Connection established - " + account.getUsername());

        if (playerStates.size() == 2) {
            gameState = GameState.MULLIGAN;
            sendInfoMessageToAll("Mulligan state");
        } else {
            sendInfoMessage(session, "Finding opponent");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        if (!playerStates.containsKey(session.getId())) {
            sendInfoMessage(session, "huinana");
            throw new AccessDeniedException("huinana");
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
