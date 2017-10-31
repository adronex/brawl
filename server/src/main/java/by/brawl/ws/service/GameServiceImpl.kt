package by.brawl.ws.service

import by.brawl.entity.Squad
import by.brawl.ws.holder.*
import by.brawl.ws.holder.gamesession.GameSession
import by.brawl.ws.holder.gamesession.GameSessionsPool
import org.springframework.stereotype.Service

@Service
internal class GameServiceImpl constructor(private val gameSessionsPool: GameSessionsPool) : GameService {

    override fun createTwoPlayersGame(firstSession: GameSession,
                                      secondSession: GameSession,
                                      firstSquad: Squad,
                                      secondSquad: Squad) {

        val roomHolder = RoomHolder()
        roomHolder.mulliganHolder.addSquad(SquadHolder(firstSquad))
        roomHolder.mulliganHolder.addSquad(SquadHolder(secondSquad))
        roomHolder.addPlayersSessions(firstSession, secondSession)
        firstSession.roomHolder = roomHolder
        secondSession.roomHolder = roomHolder
        roomHolder.gameState = GameState.MULLIGAN
        gameSessionsPool.sendMulliganData(roomHolder)
    }

    override fun setHeroesPositions(session: GameSession, battleHeroesIds: List<String>) {

        val roomHolder = getBattlefieldHolderAndCheckState(session, GameState.MULLIGAN)
        if (battleHeroesIds.size != BATTLEFIELD_HEROES_COUNT) {
            throw IllegalArgumentException("Wrong heroes in battle count. Expected: $BATTLEFIELD_HEROES_COUNT, actual: ${battleHeroesIds.size}")
        }

        val mulliganHeroes: List<HeroHolder> = roomHolder.mulliganHolder.myHeroes(session.username)
        val battleHeroes = mutableListOf<HeroHolder>()

        for (heroId in battleHeroesIds) {
            val battleHero = mulliganHeroes.find { h -> h.id == heroId } ?:
                    throw IllegalAccessException("Player ${session.username} is trying to choose hero with id $heroId from available ids: ${mulliganHeroes.map { it.id }}")

            battleHeroes.add(battleHero)
        }

        roomHolder.battleHolder.addSquad(SquadHolder(session.username, battleHeroes))

        if (roomHolder.battleHolder.isReady()) {
            roomHolder.prepareGame()
            roomHolder.gameState = GameState.PLAYING
            gameSessionsPool.sendBattlefieldData(roomHolder)
        } else {
            session.sendInfoMessage("Opponent is still choosing.")
        }
    }

    private fun getBattlefieldHolderAndCheckState(session: GameSession, gameState: GameState): RoomHolder {

        if (gameState != session.roomHolder.gameState) {
            throw IllegalStateException("Illegal game state. Expected: $gameState, actual: ${session.roomHolder.gameState}. Initiator: ${session.username}")
        }

        return session.roomHolder
    }

    companion object {

        private val BATTLEFIELD_HEROES_COUNT = 4
    }
}