package by.brawl.ws.holder

import by.brawl.entity.Squad
import by.brawl.ws.holder.gamesession.GameSession
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
                throw IllegalStateException("Two-players battlefield contains more players than maximum allowed: ${battleHeroes.size}")
            else battleHeroes.size == PLAYERS_COUNT

    fun isGameFinished() = battleHeroes.values.count { singleList -> singleList.count { it.isAlive() } == 0 } > 0

    fun addSquad(squad: Squad) {
        val heroes = squad.heroes.map { HeroHolder(it, this) }
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
            return battleHeroes[senderId] ?: throw IllegalStateException("No heroes for session key $senderId")
        }
        for ((key, value) in battleHeroes) {
            if (senderId != key) {
                return value
            }
        }
        throw IllegalStateException("Battle heroes does not contain enemy for player $senderId")
    }

    fun getAlliedHeroes(heroHolder: HeroHolder): List<HeroHolder> {
        return battleHeroes.values.maxBy { it.indexOf(heroHolder) }
                ?: throw IllegalArgumentException("Hero with id ${heroHolder.id} is absent in queue")
    }

    fun getFirstHeroFromQueue(): HeroHolder = queue.peek()

    fun getPositionOfHero(heroHolder: HeroHolder): Int {
        if (gameState == GameState.MULLIGAN) {
            return mulliganHeroes.values.maxBy { it.indexOf(heroHolder) }?.indexOf(heroHolder)
                    ?: throw IllegalArgumentException("Hero with id ${heroHolder.id} is absent in queue")
        }
        return battleHeroes.values.maxBy { it.indexOf(heroHolder) }?.indexOf(heroHolder)
                ?: throw IllegalArgumentException("Hero with id ${heroHolder.id} is absent in queue")
    }


    fun getHeroByPosition(session: GameSession, position: Int): HeroHolder {
        return getHeroByPosition(session, Math.abs(position), position > 0)
    }

    fun getHeroByPosition(session: GameSession, adaptedPosition: Int, enemy: Boolean): HeroHolder {
        val realIndex = adaptedPosition - 1
        val sessionKey: String = if (enemy) {
            battleHeroes.keys.first { it != session.id }
        } else {
            session.id
        }
        return battleHeroes[sessionKey]?.get(realIndex) ?: throw IllegalStateException("Invalid session key: $sessionKey")
    }


    companion object {
        private val PLAYERS_COUNT = 2
    }

}