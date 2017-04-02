package by.brawl.ws.pojo;

import by.brawl.entity.Account;
import by.brawl.ws.dto.HeroDto;
import by.brawl.ws.dto.SpellDto;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerStateHolder {
    private Account player;
    private WebSocketSession session;
    private Boolean readyForGame;
    private Set<SpellDto> spells = new HashSet<>();
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

    public Set<SpellDto> getSpells() {
        return spells;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
}
