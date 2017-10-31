package by.brawl.ws.holder.gamesession

import by.brawl.ws.dto.BattlefieldDto
import by.brawl.ws.dto.HeroDto
import by.brawl.ws.dto.MulliganDto
import by.brawl.ws.holder.RoomHolder
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.*

@Component
open class GameSessionsPool {

    private val gameSessions = HashMap<String, GameSession>()

    fun getSession(principalName: String): GameSession {
        return gameSessions[principalName] ?: throw IllegalArgumentException("Session for $principalName doesn't exist")
    }

    fun putSession(session: WebSocketSession) {
        val key = session.principal.name
        val value = GameSession(session)
        gameSessions.put(key, value)
    }

    private fun getAllReceiversByBattlefield(room: RoomHolder): Set<GameSession> {
        return room.connectedAccountsIds.mapNotNull { key -> gameSessions[key] }.toSet()
    }

    fun sendMulliganData(room: RoomHolder) {
        getAllReceiversByBattlefield(room).forEach { receiver ->
            val mulliganHolder = receiver.roomHolder.mulliganHolder
            val dto = MulliganDto(
                    mulliganHolder.ownHeroes(receiver.username).map { HeroDto(it) },
                    mulliganHolder.enemyHeroes(receiver.username).map { HeroDto(it) },
                    receiver.roomHolder.gameState
            )
            receiver.sendDto(dto)
        }
    }

    fun sendBattlefieldData(room: RoomHolder) {
        getAllReceiversByBattlefield(room).forEach { receiver ->
            val dto = BattlefieldDto(receiver.roomHolder, receiver.username)
            receiver.sendDto(dto)
        }
    }
}