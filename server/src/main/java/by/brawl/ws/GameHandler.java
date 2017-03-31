package by.brawl.ws;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.service.AccountService;
import by.brawl.ws.dto.GameTurnDto;
import by.brawl.ws.dto.MessageDto;
import by.brawl.ws.pojo.GameState;
import by.brawl.ws.pojo.PlayerStateHolder;
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
        if (playerStates.containsKey(session.getId()) && !GameState.END.equals(gameState)) {
            sendGameTurn(session);
            return;
        } else if (playerStates.containsKey(session.getId()) && GameState.END.equals(gameState)) {
            clearSessions();
        }

        Account account = accountService.findByEmail(session.getPrincipal().getName());

        playerStates.put(session.getId(), new PlayerStateHolder(account, session, false));

        session.sendMessage(new TextMessage(new MessageDto("Connection established - " + account.getUsername()).asJson()));

        if (playerStates.size() == 2) {
            gameState = GameState.MULLIGAN;
            sendMessageToAll(new MessageDto("Mulligan state").asJson());
        } else {
            session.sendMessage(new TextMessage(new MessageDto("Finding opponent").asJson()));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        if (!playerStates.containsKey(session.getId())) {
            session.sendMessage(new TextMessage(new MessageDto("huinana").asJson()));
            throw new AccessDeniedException("huinana");
        }

        if (GameState.MULLIGAN.equals(gameState)) {
            playerStates.get(session.getId()).setReadyForGame(true);
            if (playerStates.values()
                    .stream()
                    .filter(s -> !s.getReadyForGame())
                    .count() == 0) {
                sendMessageToAll(new MessageDto("Game started!").asJson());
                gameState = GameState.PLAYING;
                setQueue();
                sendGameTurnToAll();
            } else {
                session.sendMessage(new TextMessage
                        (new MessageDto("Opponent still choosing.").asJson())
                );
            }
        } else if (GameState.PLAYING.equals(gameState)) {
            Hero currentHero = heroesQueue.element();
            if (!playerStates.get(session.getId())
                    .getPlayer()
                    .equals(currentHero.getOwner())) {
                session.sendMessage(new TextMessage(new MessageDto("Not your turn!").asJson()));
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
                sendMessageToAll(new MessageDto("Game over!").asJson());
                clearSessions();
            }
        }
    }

    private void sendGameTurnToAll() {
        playerStates.values()
                .stream()
                .map(PlayerStateHolder::getSession)
                .forEach(this::sendGameTurn);
    }

    private void sendGameTurn(WebSocketSession session) {
        Account receiver = playerStates.get(session.getId()).getPlayer();
        try {
            session.sendMessage(new TextMessage(new GameTurnDto(gameState, heroesQueue, receiver).asJson()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAll(String message) {
        playerStates.values()
                .stream().map(PlayerStateHolder::getSession)
                .forEach(s -> {
                    try {
                        s.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
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
