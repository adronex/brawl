package by.brawl.ws.service

import by.brawl.entity.Squad
import by.brawl.service.AccountService
import by.brawl.service.SquadService
import by.brawl.ws.holder.gamesession.GameSession
import org.springframework.data.util.Pair
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class MatchmakingServiceImpl
constructor(private val accountService: AccountService,
            private val squadService: SquadService,
            private val gameService: GameService) : MatchmakingService {

    private val waitingPool = ArrayList<Pair<GameSession, Squad>>()

    override fun addInPool(session: GameSession, squadId: String) {

        val account = accountService.findByEmail(session.id)
        val squad = squadService.getWithAuthorityCheck(account, squadId)
        waitingPool.add(Pair.of(session, squad))
        // todo: remake to scheduled version
        triggerMatchmaking()
    }

    //@Scheduled
    private fun triggerMatchmaking() {
        val poolSize = waitingPool.size
        if (poolSize > 0 && poolSize % 2 == 0) {
            val first = waitingPool[0]
            waitingPool.removeAt(0)
            val second = waitingPool[0]
            waitingPool.removeAt(0)
            gameService.createTwoPlayersGame(first.first, second.first, first.second, second.second)
        }
    }

}