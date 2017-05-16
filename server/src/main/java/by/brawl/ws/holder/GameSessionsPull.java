package by.brawl.ws.holder;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameSessionsPull {

	private Map<String, GameSession> gameSessions = new HashMap<>();

	public GameSession getSession(String principalName) {
		return gameSessions.get(principalName);
	}

	public void putSession(WebSocketSession session) {
		String key = session.getPrincipal().getName();
		GameSession value = new GameSession(session);
		gameSessions.put(key, value);
	}
}
