package by.brawl.ws.holder.gamesession;

import by.brawl.util.Exceptions;
import by.brawl.util.Mappers;
import by.brawl.ws.dto.BattlefieldDto;
import by.brawl.ws.dto.MulliganDto;
import by.brawl.ws.holder.BattlefieldHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class GameSessionsPool {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionsPool.class);

	private Map<String, GameSession> gameSessions = new HashMap<>();

	public GameSession getSession(String principalName) {
		return gameSessions.get(principalName);
	}

	public void putSession(WebSocketSession session) {
		String key = session.getPrincipal().getName();
		GameSession value = new GameSession(session);
		gameSessions.put(key, value);
	}

	private Set<GameSession> getAllReceiversByBattlefield (BattlefieldHolder battlefield) {
        Set<GameSession> receivers = Mappers.asSet(battlefield.getConnectedAccountsIds(), key -> gameSessions.get(key));
        if (receivers.contains(null)) {
            throw Exceptions.produceNullPointer(LOG, "BattlefieldHolder contains null sessions!");
        }
		return receivers;
	}

	public void sendMulliganData(BattlefieldHolder battlefield) {
		getAllReceiversByBattlefield(battlefield).forEach(receiver -> {
			MulliganDto dto = new MulliganDto(
					receiver.getBattlefieldHolder().getMulliganHeroes(),
					receiver.getId(),
					receiver.getBattlefieldHolder().getGameState()
			);
			receiver.sendDto(dto);
		});
	}

	public void sendBattlefieldData(BattlefieldHolder battlefield) {
		getAllReceiversByBattlefield(battlefield).forEach(receiver -> {
			BattlefieldDto dto = new BattlefieldDto(receiver.getBattlefieldHolder(), receiver.getId());
			receiver.sendDto(dto);
		});
	}
}
