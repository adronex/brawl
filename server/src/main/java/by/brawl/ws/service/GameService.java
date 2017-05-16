package by.brawl.ws.service;

import by.brawl.entity.Squad;
import by.brawl.ws.holder.GameSession;
import org.springframework.data.util.Pair;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Set;

public interface GameService {

    void createTwoPlayersGame(GameSession firstSession, Squad firstSquad,
                              GameSession secondSession, Squad secondSquad);

    void setHeroesPositions(GameSession session,
                            List<String> heroesIds);

    void castSpell(GameSession session,
                   String spellId,
                   Integer target,
                   Boolean enemy);
}
