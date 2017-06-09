package by.brawl.ws.service;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import by.brawl.service.AccountService;
import by.brawl.service.SquadService;
import by.brawl.ws.holder.gamesession.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class MatchmakingServiceImpl implements MatchmakingService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SquadService squadService;
    @Autowired
    private GameService gameService;

    private List<Pair<GameSession, Squad>> waitingPool = new ArrayList<>();

    @Override
    public void addInPool(GameSession session, String squadId) {

        Account account = accountService.findByEmail(session.getId());
        Squad squad = squadService.getWithAuthorityCheck(account, squadId);
        waitingPool.add(Pair.of(session, squad));
        // todo: remake to scheduled version
        triggerMatchmaking();
    }

    //@Scheduled
    private void triggerMatchmaking(){
        Integer poolSize = waitingPool.size();
        if (poolSize > 0 && poolSize % 2 == 0) {
            Pair<GameSession, Squad> first = waitingPool.get(0);
            waitingPool.remove(0);
            Pair<GameSession, Squad> second = waitingPool.get(0);
            waitingPool.remove(0);
            gameService.createTwoPlayersGame(first.getFirst(), second.getFirst(), first.getSecond(), second.getSecond());
        }
    }
}
