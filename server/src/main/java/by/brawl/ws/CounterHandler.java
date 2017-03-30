package by.brawl.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CounterHandler extends TextWebSocketHandler {

    private GameState gameState = GameState.MULLIGAN;
    private List<String> history = new ArrayList<>();
    private WebSocketSession sessionFirst;
    private WebSocketSession sessionSecond;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        if (sessionFirst == null) {
            System.out.println("Connection established - player 1");
            this.sessionFirst = session;
        } else if (sessionSecond == null) {
            System.out.println("Connection established - player 2");
            this.sessionSecond = session;
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
            history.add(message.getPayload());
            sessionSecond.sendMessage(new TextMessage("GameState: " + gameState));
        }
//        if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
//            session.close();
//        } else {
//            System.out.println("Received:" + message.getPayload());
//        }
    }
}
