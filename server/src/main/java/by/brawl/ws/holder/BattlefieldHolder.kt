package by.brawl.ws.holder

import by.brawl.entity.Squad
import by.brawl.util.Exceptions
import org.slf4j.LoggerFactory
import java.util.*


class BattlefieldHolder {

    var gameState = GameState.NOT_STARTED
    var currentStep = 0

    val mulliganHeroes = HashMap<String, List<HeroHolder>>()
    private val battleHeroes = HashMap<String, List<HeroHolder>>()
    val connectedAccountsIds = HashSet<String>()
    val queue: Queue<HeroHolder> = LinkedList()
    val battleLog = mutableListOf<StepLogHolder>()

    fun isReadyForBattle() =
            if (battleHeroes.size > PLAYERS_COUNT)
                throw Exceptions.produceIllegalState(LOG, "Two-players battlefield contains more players than maximum allowed: ${battleHeroes.size}")
            else battleHeroes.size == PLAYERS_COUNT

    fun isGameFinished() = battleHeroes.values.count { singleList -> singleList.count { it.isAlive() } == 0 } > 0

    fun addSquad(squad: Squad) {
        val heroes = squad.heroes.map { HeroHolder(it) }
        mulliganHeroes.put(squad.owner.username, heroes)
        connectedAccountsIds.add(squad.owner.username)
    }

    fun addBattleHeroes(playerId: String, battleHeroes: List<HeroHolder>) {
        this.battleHeroes.put(playerId, battleHeroes)
    }

    fun prepareGame() {
        battleHeroes.values.forEach { heroHolders -> queue.addAll(heroHolders) }
        mulliganHeroes.clear()
    }

    fun moveQueueWithDeadRemoval() {
        val heroToMove = queue.remove()
        queue.add(heroToMove)

        val newQueue = queue.filter { it.isAlive() }
        queue.clear()
        queue.addAll(newQueue)
    }

    fun incrementStep() = currentStep++

    fun getBattleHeroes(senderId: String, queryForOpponentHeroes: Boolean): List<HeroHolder> {
        if (!queryForOpponentHeroes) {
            return battleHeroes[senderId] ?: throw Exceptions.produceIllegalState(LOG, "No heroes for session key $senderId")
        }
        for ((key, value) in battleHeroes) {
            if (senderId != key) {
                return value
            }
        }
        throw Exceptions.produceIllegalState(LOG, "Battle heroes does not contain enemy for player $senderId")
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(BattlefieldHolder::class.java)
        private val PLAYERS_COUNT = 2
    }

}