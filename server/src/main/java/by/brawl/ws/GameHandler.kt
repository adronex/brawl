package by.brawl.ws

import by.brawl.util.Exceptions
import by.brawl.ws.dto.ClientRequestType
import by.brawl.ws.holder.gamesession.GameSession
import by.brawl.ws.holder.gamesession.GameSessionsPool
import by.brawl.ws.service.GameService
import by.brawl.ws.service.MatchmakingService
import org.json.JSONException
import org.json.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.io.IOException
import java.util.*

// Noisia - Machine Gun
// Noisia - Hunter Theme

@Component
open class GameHandler @Autowired
constructor(private val gameSessionsPool: GameSessionsPool,
            private val matchmakingService: MatchmakingService,
            private val gameService: GameService) : TextWebSocketHandler() {

    @Throws(IOException::class)
    override fun afterConnectionEstablished(webSocketSession: WebSocketSession?) {
        gameSessionsPool.putSession(webSocketSession!!)
        val gameSession = gameSessionsPool.getSession(webSocketSession.principal.name)
        gameSession.sendKeyValue("connected", true)
    }

    @Throws(IOException::class)
    override fun handleTextMessage(webSocketSession: WebSocketSession?, message: TextMessage?) {
        try {
            val gameSession = gameSessionsPool.getSession(webSocketSession!!.principal.name)

            val request = JSONObject(message!!.payload)
            val type = request.getEnum(ClientRequestType::class.java, "type")
            val body = request.getJSONObject("body")

            if (ClientRequestType.INITIAL == type) {
                handleInitRequest(gameSession, body)
            }

            if (ClientRequestType.CHOOSE_HEROES == type) {
                handleChooseHeroesRequest(gameSession, body)
            }

            if (ClientRequestType.CAST_SPELL == type) {
                handleCastSpellRequest(gameSession, body)
            }
        } catch (e: Exception) {
            Exceptions.logError(LOG, e, e.message ?: "Unspecified exception happened.")
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
        val spellId = body.optString("spellId")
        var target: Int? = null
        body.optInt("target")
        try {
            target = body.getInt("target")
        } catch (ignored: JSONException) {
        }

        var forEnemy: Boolean? = null
        try {
            forEnemy = body.getBoolean("forEnemy")
        } catch (ignored: JSONException) {
        }

        gameService.castSpell(session, spellId, target, forEnemy)
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(GameHandler::class.java)
    }
}