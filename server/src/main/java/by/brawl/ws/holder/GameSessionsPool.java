package by.brawl.ws.holder;

import by.brawl.util.Exceptions;
import by.brawl.ws.newdto.BattlefieldDto;
import by.brawl.ws.newdto.MulliganDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
		Set<GameSession> receivers = battlefield.getConnectedAccountsIds()
				.stream()
				.map(key -> gameSessions.get(key))
				.collect(Collectors.toSet());
		if (receivers.contains(null)) {
			throw Exceptions.produceNullPointer(LOG, "BattlefieldHolder contains null sessions!");
		}
		return receivers;
	}

	public void sendMulliganData(BattlefieldHolder battlefield) {
		getAllReceiversByBattlefield(battlefield).forEach(receiver -> {
			MulliganDto dto = new MulliganDto(
					receiver.getBattlefieldHolder().getMulliganHeroes(),
					receiver.getBattlefieldHolder().getHeroSpells(),
					receiver.getId()
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
