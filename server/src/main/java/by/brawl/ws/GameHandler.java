package by.brawl.ws;

import by.brawl.util.Exceptions;
import by.brawl.ws.dto.ClientRequestType;
import by.brawl.ws.holder.GameSession;
import by.brawl.ws.holder.GameSessionsPool;
import by.brawl.ws.service.GameService;
import by.brawl.ws.service.MatchmakingService;
import org.json.JSONArray;
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

// todo: Noisia - Machine Gun
// todo: Noisia - Hunter Theme

// todo: matchmaking service
// todo: game service
// steps:
// 1. choose squad.
// OPEN WEBSOCKET.
// 2. send it's id in mm service
// 3. mm service create room with 2 players and send pre-game squads DTOs.
// MULLIGAN STATE
// 4. player send heroes and positions (order matters).
// 5. waiting for second player if necessary.
// 6. send queue, enemy positions.
// PLAYING STATE
// 7. send valid spell id and positions array ({self: [], enemy: [0, 3]}.
// 8. game service validates input data.
// 9. set triggered spells as exposed.
// 10. send updated queue, heroes through self/enemy parser.
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
		gameSession.sendInfoMessage("Connected");
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
		String squadId = body.getString("squadId");
		matchmakingService.addInPool(session, squadId);
	}

	private void handleChooseHeroesRequest(GameSession session, JSONObject body) {
		JSONArray requestArray = body.getJSONArray("heroes");

		List<String> heroes = new ArrayList<>();
		requestArray.toList().forEach(o -> heroes.add((String) o));

		//todo: remove next block with accountService
		{
			heroes.clear();
			if ("adronex303@gmail.com".equals(session.getId())) {
				heroes.add("1");
				heroes.add("2");
			} else if ("adronex_@mail.ru".equals(session.getId())) {
				heroes.add("3");
				heroes.add("4");
			}
		}

		gameService.setHeroesPositions(session, heroes);
	}

	private void handleCastSpellRequest(GameSession session, JSONObject body) {
		String spellId = "1"; // TODO: remove mock and get this spell from request
		Integer target = body.optInt("target");
		Boolean enemy = body.optBoolean("enemy");
		gameService.castSpell(session, spellId, target, enemy);
	}
}
