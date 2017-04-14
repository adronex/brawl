package by.brawl.ws.service;

import by.brawl.entity.Squad;
import org.springframework.data.util.Pair;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface GameService {

    void createTwoPlayersGame(WebSocketSession firstSession, Squad firstSquad,
                              WebSocketSession secondSession, Squad secondSquad);

    void setHeroesPositions(WebSocketSession session,
                            List<String> heroesIds);
}
