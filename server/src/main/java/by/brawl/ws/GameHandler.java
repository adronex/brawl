package by.brawl.ws;

import by.brawl.util.Exceptions;
import by.brawl.ws.dto.ClientRequestType;
import by.brawl.ws.holder.gamesession.GameSession;
import by.brawl.ws.holder.gamesession.GameSessionsPool;
import by.brawl.ws.service.GameService;
import by.brawl.ws.service.MatchmakingService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Noisia - Machine Gun
// Noisia - Hunter Theme

@Component
public class GameHandler extends TextWebSocketHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GameHandler.class);

	private GameSessionsPool gameSessionsPool;
	private MatchmakingService matchmakingService;
	private GameService gameService;

	@Autowired
	public GameHandler(GameSessionsPool gameSessionsPool,
					   MatchmakingService matchmakingService,
					   GameService gameService) {

		this.gameSessionsPool = gameSessionsPool;
		this.matchmakingService = matchmakingService;
		this.gameService = gameService;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws IOException {
		gameSessionsPool.putSession(webSocketSession);
		GameSession gameSession = gameSessionsPool.getSession(webSocketSession.getPrincipal().getName());
		if (gameSession == null) {
			throw Exceptions.produceNullPointer(LOG, "Session wasn't successfully added!");
		}
		gameSession.sendKeyValue("connected", true);
	}

	@Override
	protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws IOException {

		GameSession gameSession = gameSessionsPool.getSession(webSocketSession.getPrincipal().getName());
		if (gameSession == null) {
			throw Exceptions.produceNullPointer(LOG, "Session wasn't successfully added!");
		}

		JSONObject request = new JSONObject(message.getPayload());
		ClientRequestType type = request.getEnum(ClientRequestType.class, "type");
		JSONObject body = request.getJSONObject("body");

		if (ClientRequestType.INITIAL.equals(type)) {
			handleInitRequest(gameSession, body);
		}

		if (ClientRequestType.CHOOSE_HEROES.equals(type)) {
			handleChooseHeroesRequest(gameSession, body);
		}

		if (ClientRequestType.CAST_SPELL.equals(type)) {
			handleCastSpellRequest(gameSession, body);
		}
	}

	private void handleInitRequest(GameSession session, JSONObject body) {
		String squadId = body.optString("squadId", null);
		matchmakingService.addInPool(session, squadId);
	}

	private void handleChooseHeroesRequest(GameSession session, JSONObject body) {
		JSONArray requestArray = body.getJSONArray("heroes");

		List<String> heroes = new ArrayList<>();
		requestArray.toList().forEach(o -> heroes.add((String) o));

		gameService.setHeroesPositions(session, heroes);
	}

	private void handleCastSpellRequest(GameSession session, JSONObject body) {
		String spellId = body.optString("spellId");
		Integer target = null; body.optInt("target");
		try {
			target = body.getInt("target");
		} catch (JSONException ignored) { }
		Boolean forEnemy = null;
		try {
			forEnemy = body.getBoolean("forEnemy");
		} catch (JSONException ignored) { }
		gameService.castSpell(session, spellId, target, forEnemy);
	}
}
