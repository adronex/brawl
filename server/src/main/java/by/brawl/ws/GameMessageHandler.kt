package by.brawl.ws

import by.brawl.ws.dto.ClientRequestType
import by.brawl.ws.holder.gamesession.GameSession
import by.brawl.ws.holder.gamesession.GameSessionsPool
import by.brawl.ws.huihui.SpellService
import by.brawl.ws.service.GameService
import by.brawl.ws.service.MatchmakingService
import org.json.JSONObject
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.*

// Noisia - Machine Gun
// Noisia - Hunter Theme

@Component
open class GameMessageHandler(private val gameSessionsPool: GameSessionsPool,
                              private val matchmakingService: MatchmakingService,
                              private val gameService: GameService,
                              private val spellService: SpellService) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(webSocketSession: WebSocketSession) {
        gameSessionsPool.putSession(webSocketSession)
        val gameSession = gameSessionsPool.getSession(webSocketSession.principal.name)
        gameSession.sendKeyValue("connected", true)
    }

    override fun handleTextMessage(webSocketSession: WebSocketSession, message: TextMessage) {
        val gameSession = gameSessionsPool.getSession(webSocketSession.principal.name)

        val request = JSONObject(message.payload)
        val type: ClientRequestType = request.getEnum(ClientRequestType::class.java, "type")
        val body = request.getJSONObject("body")

        when (type) {
            ClientRequestType.INITIAL -> handleInitRequest(gameSession, body)
            ClientRequestType.CHOOSE_HEROES -> handleChooseHeroesRequest(gameSession, body)
            ClientRequestType.CAST_SPELL -> handleCastSpellRequest(gameSession, body)
        }
    }

    private fun handleInitRequest(session: GameSession, body: JSONObject) {
        val squadId = body.optString("squadId", null)
        matchmakingService.addInPool(session, squadId)
    }

    private fun handleChooseHeroesRequest(session: GameSession, body: JSONObject) {
        val requestArray = body.getJSONArray("heroes")

        val heroes = ArrayList<String>()
        requestArray.toList().forEach { o -> heroes.add(o as String) }

        gameService.setHeroesPositions(session, heroes)
    }

    private fun handleCastSpellRequest(session: GameSession, body: JSONObject) {
        val spellId = body.getString("spellId")
        val targetEnemy = body.getBoolean("targetEnemy")
        val targetPosition = body.getInt("targetPosition")
        if (targetPosition !in 0..3) {
            throw IllegalArgumentException("Incorrect target position: $targetPosition, allowed: [0, 3]")
        }

        spellService.cast(session, spellId, targetPosition, targetEnemy)
    }
}