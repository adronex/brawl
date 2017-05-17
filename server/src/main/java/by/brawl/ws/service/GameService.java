package by.brawl.ws.service;

import by.brawl.entity.Squad;
import by.brawl.ws.holder.GameSession;

import java.util.List;

public interface GameService {

    void createTwoPlayersGame(GameSession firstSession, Squad firstSquad,
                              GameSession secondSession, Squad secondSquad);

    void setHeroesPositions(GameSession session,
                            List<String> heroesIds);

    void castSpell(GameSession session,
                   String spellId,
                   Integer victimPosition,
                   Boolean forEnemy);
}
