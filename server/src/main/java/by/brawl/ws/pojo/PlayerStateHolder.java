package by.brawl.ws.pojo;

import by.brawl.entity.Account;
import org.springframework.web.socket.WebSocketSession;

public class PlayerStateHolder {
    private Account player;
    private WebSocketSession session;
    private Boolean readyForGame;
    private Boolean isAlive;

    public PlayerStateHolder(Account player, WebSocketSession session, Boolean readyForGame) {
        this.player = player;
        this.session = session;
        this.readyForGame = readyForGame;
    }

    public Account getPlayer() {
        return player;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public Boolean getReadyForGame() {
        return readyForGame;
    }

    public void setReadyForGame(Boolean readyForGame) {
        this.readyForGame = readyForGame;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
}
