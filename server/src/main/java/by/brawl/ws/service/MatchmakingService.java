package by.brawl.ws.service;

import by.brawl.entity.Account;
import by.brawl.ws.holder.GameSession;
import org.springframework.web.socket.WebSocketSession;

public interface MatchmakingService {
    void addInPool(GameSession session, String squadId);
}
