package by.brawl.ws.service;

import by.brawl.ws.holder.gamesession.GameSession;

public interface MatchmakingService {
    void addInPool(GameSession session, String squadId);
}
