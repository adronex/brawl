package by.brawl.ws.service;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    @Override
    public void createTwoPlayersGame(Pair<Account, Squad> firstPlayer,
                                     Pair<Account, Squad> secondPlayer) {

    }
}
