package by.brawl.ws.holder;

import by.brawl.util.Exceptions;
import by.brawl.ws.dto.JsonDto;
import by.brawl.ws.dto.MessageDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameSession {

	private static final Logger LOG = LoggerFactory.getLogger(GameSession.class);

	private String id;
	private WebSocketSession session;
	private BattlefieldHolder battlefieldHolder;

	public GameSession(WebSocketSession session) {
		this.session = session;
		id = session.getPrincipal().getName();
	}

	public void sendInfoMessage(String text) {
		sendDto(new MessageDto(text));
	}

	public void sendDto(JsonDto dto) {
		try {
			session.sendMessage(new TextMessage(dto.asJson()));
		} catch (IOException e) {
			Exceptions.logError(LOG, e, "Web socket message sending threw error");
		}
	}

	public void sendKeyValue(String key, Object value) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(key, value);
			session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(map)));
		} catch (IOException e) {
			Exceptions.logError(LOG, e, "Web socket message sending threw error");
		}
	}

	public String getId() {
		return id;
	}

	public BattlefieldHolder getBattlefieldHolder() {
		return battlefieldHolder;
	}

	public void setBattlefieldHolder(BattlefieldHolder battlefieldHolder) {
		this.battlefieldHolder = battlefieldHolder;
	}
}
