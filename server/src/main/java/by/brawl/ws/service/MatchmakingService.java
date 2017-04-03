package by.brawl.ws.service;

import by.brawl.entity.Account;
import org.springframework.web.socket.WebSocketSession;

public interface MatchmakingService {
    void addInPool(WebSocketSession session, String squadId);
}
