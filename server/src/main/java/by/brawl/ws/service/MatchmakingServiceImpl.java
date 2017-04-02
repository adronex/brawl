package by.brawl.ws.service;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import by.brawl.service.SquadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class MatchmakingServiceImpl implements MatchmakingService {

    @Autowired
    private SquadService squadService;
    @Autowired
    private GameService gameService;

    private List<Pair<Account, Squad>> waitingPool = new ArrayList<>();

    @Override
    public void addInPool(Account account, String squadId) {
        Squad squad = squadService.getWithAuthorityCheck(account, squadId);
        waitingPool.add(Pair.of(account, squad));
        // todo: remake to scheduled version
        triggerMatchmaking();
    }

    //@Scheduled
    private void triggerMatchmaking(){
        Integer poolSize = waitingPool.size();
        if (poolSize > 0 && poolSize % 2 == 0) {
            Pair<Account, Squad> first = waitingPool.get(0);
            waitingPool.remove(0);
            Pair<Account, Squad> second = waitingPool.get(0);
            waitingPool.remove(0);
            gameService.createTwoPlayersGame(first, second);
        }
    }
}
