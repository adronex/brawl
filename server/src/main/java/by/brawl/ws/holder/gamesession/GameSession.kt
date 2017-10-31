package by.brawl.ws.holder.gamesession

import by.brawl.ws.dto.JsonDto
import by.brawl.ws.dto.MessageDto
import by.brawl.ws.holder.RoomHolder
import org.codehaus.jackson.map.ObjectMapper
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.*

class GameSession(private val session: WebSocketSession) {

    val username: String = session.principal.name
    lateinit var roomHolder: RoomHolder

    fun sendInfoMessage(text: String) {
        sendDto(MessageDto(text))
    }

    fun sendDto(dto: JsonDto) {
        session.sendMessage(TextMessage(dto.asJson()))
    }

    fun sendKeyValue(key: String, value: Any) {
        val map = HashMap<String, Any>()
        map.put(key, value)
        session.sendMessage(TextMessage(ObjectMapper().writeValueAsString(map)))
    }

}