package by.brawl.ws.holder

import by.brawl.ws.holder.gamesession.GameSession
import java.util.*


class RoomHolder {

    var gameState = GameState.NOT_STARTED
    var currentStep = 0

    val mulliganHolder:BattlefieldHolder = BattlefieldHolder()
    val battleHolder:BattlefieldHolder = BattlefieldHolder()
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

}