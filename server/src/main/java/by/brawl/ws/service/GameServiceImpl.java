package by.brawl.ws.service;

import by.brawl.entity.Squad;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class GameServiceImpl implements GameService {

    @Override
    public void createTwoPlayersGame(Pair<WebSocketSession, Squad> firstPlayer,
                                     Pair<WebSocketSession, Squad> secondPlayer) {

    }
}
