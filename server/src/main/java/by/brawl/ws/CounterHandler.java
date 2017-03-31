package by.brawl.ws;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.service.AccountService;
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
public class CounterHandler extends TextWebSocketHandler {

    private GameState gameState = GameState.NOT_STARTED;
    private List<String> history = new ArrayList<>();

    private Map<String, Account> players = new HashMap<>();
    private Map<String, WebSocketSession> sessions = new HashMap<>();
    private Map<String, Boolean> readyForGame = new HashMap<>();

    private Queue<Hero> heroesQueue = new LinkedList<>();

    private AccountService accountService;

    @Autowired
    public CounterHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        if (players.containsKey(session.getId()) && !GameState.END.equals(gameState)) {
            sendQueueMessage(session);
            return;
        } else if (players.containsKey(session.getId()) && GameState.END.equals(gameState)) {
            clearSessions();
        }

        Account account = accountService.findByEmail(session.getPrincipal().getName());

        players.put(session.getId(), account);
        sessions.put(session.getId(), session);
        readyForGame.put(session.getId(), false);
        session.sendMessage(new TextMessage(new MessageDto("Connection established - " + account.getUsername()).asJson()));

        if (players.size() == 2) {
            gameState = GameState.MULLIGAN;
            sendMessageToAll(new MessageDto("Mulligan state").asJson());
        } else {
            session.sendMessage(new TextMessage(new MessageDto("Finding opponent").asJson()));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        if (!sessions.containsKey(session.getId())) {
            session.sendMessage(new TextMessage(new MessageDto("huinana").asJson()));
            throw new AccessDeniedException("huinana");
        }

        if (GameState.MULLIGAN.equals(gameState)) {
            readyForGame.put(session.getId(), true);
            if (!readyForGame.containsValue(false)) {
                sendMessageToAll(new MessageDto("Game started!").asJson());
                gameState = GameState.PLAYING;
                setQueue();
                sendQueueMessageToAll();
            } else {
                session.sendMessage(new TextMessage
                        (new MessageDto("Opponent still choosing.").asJson())
                );
            }
        } else if (GameState.PLAYING.equals(gameState)) {
            Hero currentHero = heroesQueue.element();
            if (!players.get(session.getId()).equals(currentHero.getOwner())) {
                session.sendMessage(new TextMessage(new MessageDto("Not your turn!").asJson()));
                return;
            }
            // todo: check for spell is missing
            currentHero.hit(15);
            heroesQueue.remove();
            heroesQueue.add(currentHero);
            checkQueue();
            checkGameIsFinished();
            sendQueueMessageToAll();
            if (GameState.END.equals(gameState)) {
                sendMessageToAll(new MessageDto("Game over!").asJson());
                clearSessions();
            }
        }
    }

    private void sendQueueMessageToAll() {
        sessions.values().forEach(this::sendQueueMessage);
    }

    private void sendQueueMessage(WebSocketSession session) {
        Account receiver = players.get(session.getId());
        try {
            session.sendMessage(new TextMessage(new GameTurnDto(gameState, heroesQueue, receiver).asJson()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAll(String message) {
        sessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setQueue(){
        players.values().forEach(a -> heroesQueue.addAll(a.getSquads().iterator().next().getHeroes()));
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

        Map<Account, Boolean> stillInPlay = new HashMap<>();
        players.values().forEach(a -> {
            stillInPlay.put(a, splitList.get(a) != null);
        });

        if (stillInPlay.containsValue(false)) {
            gameState = GameState.END;
        }
    }

    private void clearSessions() {
        sessions.values().forEach(s -> {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sessions.clear();
    }
}
