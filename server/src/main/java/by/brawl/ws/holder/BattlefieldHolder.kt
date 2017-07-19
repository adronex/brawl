package by.brawl.ws.holder

import by.brawl.entity.Squad
import by.brawl.util.Exceptions
import org.slf4j.LoggerFactory
import java.util.*


class BattlefieldHolder {

    var gameState = GameState.NOT_STARTED
    var currentStep: Int = 0

    val mulliganHeroes = HashMap<String, List<HeroHolder>>()
    val battleHeroes = HashMap<String, List<HeroHolder>>()
    val connectedAccountsIds = HashSet<String>()
    val queue: Queue<HeroHolder> = LinkedList()
    val battleLog: List<StepLogHolder> = ArrayList()

    fun addSquad(squad: Squad) {
        val heroes = squad.heroes.map { HeroHolder(it) }
        mulliganHeroes.put(squad.owner.username, heroes)
        connectedAccountsIds.add(squad.owner.username)
    }

    fun addBattleHeroes(playerId: String, battleHeroes: List<HeroHolder>) {
        this.battleHeroes.put(playerId, battleHeroes)
    }

    val isReadyForBattle: Boolean?
        get() {
            if (battleHeroes.size > PLAYERS_COUNT) {
                throw Exceptions.produceIllegalState(
                        LOG,
                        "Two-players battlefield contains more players than maximum allowed: {0}",
                        battleHeroes.size
                )
            }
            return battleHeroes.size == PLAYERS_COUNT
        }

    fun prepareGame() {
        battleHeroes.values.forEach { heroHolders -> queue.addAll(heroHolders) }
        mulliganHeroes.clear()
    }

    fun moveQueueWithDeadRemoval() {
        val heroToMove = queue.remove()
        queue.add(heroToMove)

        val newQueue = queue.filter { it.isAlive }
        queue.clear()
        queue.addAll(newQueue)
    }

    fun incrementStep() {
        currentStep++
    }

    val isGameFinished: Boolean?
        get() {
            var gameFinished: Boolean? = false
            for ((_, value) in battleHeroes) {
                if (value.count{ it.isAlive } == 0) {
                    gameFinished = true
                }
            }
            return gameFinished
        }

    fun getBattleHeroes(senderId: String, queryForOpponentHeroes: Boolean?): List<HeroHolder> {
        if (queryForOpponentHeroes != null && !queryForOpponentHeroes) {
            return battleHeroes[senderId] ?: throw Exceptions.produceIllegalState(LOG, "No heroes for session key {0}", senderId)
        }
        for ((key, value) in battleHeroes) {
            if (senderId != key) {
                return value
            }
        }
        throw Exceptions.produceIllegalState(LOG, "Battle heroes does not contain enemy for player {0}", senderId)
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(BattlefieldHolder::class.java)
        private val PLAYERS_COUNT = 2
    }
}
