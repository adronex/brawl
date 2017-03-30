package by.brawl.ws;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.service.AccountService;
import by.brawl.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
public class CounterHandler extends TextWebSocketHandler {

    private GameState gameState = GameState.MULLIGAN;
    private List<String> history = new ArrayList<>();
    private WebSocketSession sessionFirst;
    private WebSocketSession sessionSecond;
    private Account accountFirst;
    private Account accountSecond;

    private Queue<Hero> heroesQueue = new PriorityQueue<>();

    private AccountService accountService;

    @Autowired
    public CounterHandler(AccountService accountService) {
        this.accountService = accountService;
        accountFirst = accountService.findByEmail("adronex303@gmail.com");
        accountSecond = accountService.findByEmail("adronex_@mail.ru");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        if (sessionFirst == null) {
            System.out.println("Connection established - player 1");
            this.sessionFirst = session;
        } else if (sessionSecond == null) {
            System.out.println("Connection established - player 2");
            this.sessionSecond = session;
        }
        if (sessionFirst != null && sessionSecond != null) {
            setQueue();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        if (!history.isEmpty()) {
            if (GameState.MULLIGAN.equals(gameState)) {
                gameState = GameState.FIRST_PLAYER_TURN;
            }
            if (GameState.FIRST_PLAYER_TURN.equals(gameState)) {
                gameState = GameState.SECOND_PLAYER_TURN;
            } else if (GameState.SECOND_PLAYER_TURN.equals(gameState)) {
                gameState = GameState.FIRST_PLAYER_TURN;
            }
        }
        history.add(message.getPayload());

        if (sessionFirst != null && sessionFirst.isOpen()) {
            sessionFirst.sendMessage(new TextMessage("GameState: " + gameState));
        }

        if (sessionSecond != null && sessionSecond.isOpen()) {
            sessionSecond.sendMessage(new TextMessage("GameState: " + gameState));
        }
//        if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
//            session.close();
//        } else {
//            System.out.println("Received:" + message.getPayload());
//        }
    }

    private void setQueue(){
        heroesQueue.addAll(accountFirst.getSquads().iterator().next().getHeroes());
        heroesQueue.addAll(accountSecond.getSquads().iterator().next().getHeroes());
    }
}
