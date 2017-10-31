package by.brawl.ws.holder

import by.brawl.entity.Squad
import by.brawl.ws.holder.gamesession.GameSession
import java.util.*


class RoomHolder {

    var gameState = GameState.NOT_STARTED
    var currentStep = 0

    val mulliganHolder:BattlefieldHolder = BattlefieldHolder()
    val battleHolder:BattlefieldHolder = BattlefieldHolder()
//    private val battleHeroes = HashMap<String, List<HeroHolder>>()
    val connectedAccountsIds = mutableSetOf<String>()
    val queue: Queue<HeroHolder> = LinkedList()
    val battleLog = mutableListOf<StepLogHolder>()

    fun isGameFinished() = battleHolder.allHeroes().any { it.isAlive() }

    fun addPlayersSessions(firstSession: GameSession,
                           secondSession: GameSession) {
        connectedAccountsIds.add(firstSession.username)
        connectedAccountsIds.add(secondSession.username)
    }

    fun prepareGame() {
        battleHolder.allHeroes().forEach { queue.add(it) }
    }

    fun moveQueueWithDeadRemoval() {
        val heroToMove = queue.remove()
        queue.add(heroToMove)

        val newQueue = queue.filter { it.isAlive() }
        queue.clear()
        queue.addAll(newQueue)
    }

    fun incrementStep() = currentStep++

    fun getFirstHeroFromQueue(): HeroHolder = queue.peek()

//    fun getPositionOfHero(heroHolder: HeroHolder): Int {
//        if (gameState == GameState.MULLIGAN) {
//            return mulliganHeroes.values.maxBy { it.indexOf(heroHolder) }?.indexOf(heroHolder)
//                    ?: throw IllegalArgumentException("Hero with id ${heroHolder.id} is absent in queue")
//        }
//        return battleHeroes.values.maxBy { it.indexOf(heroHolder) }?.indexOf(heroHolder)
//                ?: throw IllegalArgumentException("Hero with id ${heroHolder.id} is absent in queue")
//    }


//    fun getHeroByPosition(session: GameSession, position: Int): HeroHolder {
//        return getHeroByPosition(session, Math.abs(position), position > 0)
//    }
//
//    fun getHeroByPosition(session: GameSession, adaptedPosition: Int, enemy: Boolean): HeroHolder {
//        val realIndex = adaptedPosition - 1
//        val sessionKey: String = if (enemy) {
//            battleHeroes.keys.first { it != session.username }
//        } else {
//            session.username
//        }
//        return battleHeroes[sessionKey]?.get(realIndex) ?: throw IllegalStateException("Invalid session key: $sessionKey")
//    }


    companion object {
        private val PLAYERS_COUNT = 2
    }

}