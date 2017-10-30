package by.brawl.ws.holder.gamesession

import by.brawl.ws.dto.BattlefieldDto
import by.brawl.ws.dto.MulliganDto
import by.brawl.ws.holder.BattlefieldHolder
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.*

@Component
open class GameSessionsPool {

    private val gameSessions = HashMap<String, GameSession>()
    // todo: remove nulls
    fun getSession(principalName: String): GameSession {
        return gameSessions[principalName]!!
    }

    fun putSession(session: WebSocketSession) {
        val key = session.principal.name
        val value = GameSession(session)
        gameSessions.put(key, value)
    }

    private fun getAllReceiversByBattlefield(battlefield: BattlefieldHolder): Set<GameSession> {
        return battlefield.connectedAccountsIds.mapNotNull { key -> gameSessions[key] }.toSet()
    }

    fun sendMulliganData(battlefield: BattlefieldHolder) {
        getAllReceiversByBattlefield(battlefield).forEach { receiver ->
            val dto = MulliganDto(
                    receiver.battlefieldHolder.mulliganHeroes,
                    receiver.id,
                    receiver.battlefieldHolder.gameState
            )
            receiver.sendDto(dto)
        }
    }

    fun sendBattlefieldData(battlefield: BattlefieldHolder) {
        getAllReceiversByBattlefield(battlefield).forEach { receiver ->
            val dto = BattlefieldDto(receiver.battlefieldHolder, receiver.id)
            receiver.sendDto(dto)
        }
    }
}