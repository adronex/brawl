package by.brawl.ws.service;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import org.springframework.data.util.Pair;

public interface GameService {

    void createTwoPlayersGame(Pair<Account, Squad> firstPlayer,
                              Pair<Account, Squad> secondPlayer);
}
