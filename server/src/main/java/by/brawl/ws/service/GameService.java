package by.brawl.ws.service;

import by.brawl.entity.Squad;
import org.springframework.data.util.Pair;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface GameService {

    void createTwoPlayersGame(Pair<WebSocketSession, Squad> firstPlayer,
                              Pair<WebSocketSession, Squad> secondPlayer);

    void setHeroesPositions(WebSocketSession session,
                            List<String> heroesIds);
}
