package by.brawl.ws.service;

import by.brawl.entity.Account;

public interface MatchmakingService {
    void addInPool(Account account, String squadId);
}
