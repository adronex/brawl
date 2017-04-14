package by.brawl.ws.service;

import by.brawl.entity.Squad;
import org.springframework.data.util.Pair;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Set;

public interface GameService {

    void createTwoPlayersGame(WebSocketSession firstSession, Squad firstSquad,
                              WebSocketSession secondSession, Squad secondSquad);

    void setHeroesPositions(WebSocketSession session,
                            List<String> heroesIds);

    void castSpell(WebSocketSession session,
                   String spellId,
                   Set<Integer> myTargets,
                   Set<Integer> enemyTargets);
}
