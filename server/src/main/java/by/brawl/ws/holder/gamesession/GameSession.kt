package by.brawl.ws.holder.gamesession

import by.brawl.util.Exceptions
import by.brawl.ws.dto.JsonDto
import by.brawl.ws.dto.MessageDto
import by.brawl.ws.holder.BattlefieldHolder
import org.codehaus.jackson.map.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.io.IOException
import java.util.*

class GameSession(private val session: WebSocketSession) {

    val id: String = session.principal.name
    lateinit var battlefieldHolder: BattlefieldHolder

    fun sendInfoMessage(text: String) {
        sendDto(MessageDto(text))
    }

    fun sendDto(dto: JsonDto) {
        try {
            session.sendMessage(TextMessage(dto.asJson()))
        } catch (e: IOException) {
            Exceptions.logError(LOG, e, "Web socket message sending threw error")
        }

    }

    fun sendKeyValue(key: String, value: Any) {
        try {
            val map = HashMap<String, Any>()
            map.put(key, value)
            session.sendMessage(TextMessage(ObjectMapper().writeValueAsString(map)))
        } catch (e: IOException) {
            Exceptions.logError(LOG, e, "Web socket message sending threw error")
        }

    }

    companion object {

        private val LOG = LoggerFactory.getLogger(GameSession::class.java)
    }

}