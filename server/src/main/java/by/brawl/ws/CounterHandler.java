package by.brawl.ws;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.service.AccountService;
import by.brawl.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
public class CounterHandler extends TextWebSocketHandler {

    private GameState gameState = GameState.MULLIGAN;
    private List<String> history = new ArrayList<>();

    private Map<String, Account> players = new HashMap<>();
    private Map<String, WebSocketSession> sessions = new HashMap<>();

    private Queue<Hero> heroesQueue = new PriorityQueue<>();

    private AccountService accountService;

    @Autowired
    public CounterHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        if (players.containsKey(session.getId()) || players.size() >= 2) {
            session.sendMessage(new TextMessage("Access denied!"));
            session.close();
            throw new AccessDeniedException("huinana");
        }

        Account account = accountService.findByEmail(session.getPrincipal().getName());

        if (players.containsValue(account)) {
            session.sendMessage(new TextMessage("Access denied!"));
            throw new AccessDeniedException("huinana");
        }

        players.put(session.getId(), account);
        sessions.put(session.getId(), session);
        System.out.println("Connection established - " + account.getUsername());
        session.sendMessage(new TextMessage("Connection established - " + account.getUsername()));

        if (players.size() == 2) {
            setQueue();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        if (!sessions.containsKey(session.getId())) {
            session.sendMessage(new TextMessage("huinana"));
            throw new AccessDeniedException("huinana");
        }


        if (GameState.MULLIGAN.equals(gameState)) {
            sendMessageToAll(heroesQueue.element().toString());
            gameState = GameState.PLAYING;
        } else if (GameState.PLAYING.equals(gameState)) {
            Hero currentHero = heroesQueue.element();
            if (!players.get(session.getId()).equals(currentHero.getOwner())) {
                session.sendMessage(new TextMessage("Not your turn!"));
                return;
            }
            heroesQueue.remove();
            // todo: check for spell is missing
            heroesQueue.add(currentHero);
            sendMessageToAll(heroesQueue.element().toString());
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
}
