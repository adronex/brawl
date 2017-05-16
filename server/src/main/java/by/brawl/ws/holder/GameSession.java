package by.brawl.ws.holder;

import by.brawl.util.Exceptions;
import by.brawl.ws.newdto.JsonDto;
import by.brawl.ws.newdto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class GameSession {

	private static final Logger LOG = LoggerFactory.getLogger(GameSession.class);
	private WebSocketSession session;
	private String id;

	public GameSession(WebSocketSession session) {
		this.session = session;
		id = session.getPrincipal().getName();
	}

	public String getId() {
		return id;
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
}
