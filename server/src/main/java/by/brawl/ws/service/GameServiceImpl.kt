package by.brawl.ws.service

import by.brawl.entity.Squad
import by.brawl.util.Exceptions
import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.GameState
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.gamesession.GameSession
import by.brawl.ws.holder.gamesession.GameSessionsPool
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import java.util.ArrayList
import java.util.Arrays

@Service
internal class GameServiceImpl constructor(val spellCastService: SpellCastService,
                                           val gameSessionsPool: GameSessionsPool) : GameService {

    override fun createTwoPlayersGame(firstSession: GameSession,
                                      secondSession: GameSession,
                                      firstSquad: Squad,
                                      secondSquad: Squad) {

        val battlefieldHolder = BattlefieldHolder()
        battlefieldHolder.addSquad(firstSquad)
        battlefieldHolder.addSquad(secondSquad)
        firstSession.battlefieldHolder = battlefieldHolder
        secondSession.battlefieldHolder = battlefieldHolder
        battlefieldHolder.gameState = GameState.MULLIGAN
        gameSessionsPool.sendMulliganData(battlefieldHolder)
    }

    override fun setHeroesPositions(session: GameSession, heroesIds: List<String>) {

        val battlefieldHolder = getBattlefieldHolderAndCheckState(session, GameState.MULLIGAN)
        if (heroesIds.size != BATTLEFIELD_HEROES_COUNT) {
            throw Exceptions.produceIllegalArgument(
                    LOG,
                    "Wrong heroes in battle count. Expected: {0}, actual: {1}",
                    BATTLEFIELD_HEROES_COUNT,
                    heroesIds.size
            )
        }

        val mulliganHeroes = battlefieldHolder.mulliganHeroes[session.id] ?: throw Exceptions.produceIllegalState(LOG, "No heroes for session key {0}", session.id)
        val battleHeroes = ArrayList<HeroHolder>()

        for (heroId in heroesIds) {
            val battleHero = mulliganHeroes.find { h -> h.id == heroId } ?:
                    throw Exceptions.produceAccessDenied(
                            LOG,
                            "Player {0} trying to choose hero with id {1} from available ids: {2}",
                            session.id,
                            heroId,
                            mulliganHeroes.map { it.id })

            battleHeroes.add(battleHero)
        }

        battlefieldHolder.addBattleHeroes(session.id, battleHeroes)

        if (battlefieldHolder.isReadyForBattle!!) {
            battlefieldHolder.prepareGame()
            battlefieldHolder.gameState = GameState.PLAYING
            gameSessionsPool.sendBattlefieldData(battlefieldHolder)
        } else {
            session.sendInfoMessage("Opponent is still choosing.")
        }
    }

    override fun castSpell(session: GameSession, spellId: String, victimPosition: Int?, forEnemy: Boolean?) {

        var battlefieldHolder = getBattlefieldHolderAndCheckState(session, GameState.PLAYING)
        val playerKey = session.id
        val currentHero = battlefieldHolder.queue.element()

        if (!battlefieldHolder.getBattleHeroes(playerKey, false).contains(currentHero)) {
            throw Exceptions.produceAccessDenied(
                    LOG,
                    "Player {0} tries to cast spell in opponents turn",
                    playerKey
            )
        }

        // todo: check victimPosition and forEnemy for both being null or not null
        if (!isSpellValid(currentHero, spellId, playerKey)) {
            return
        }

        battlefieldHolder = spellCastService.castSpell(spellId,
                session.id,
                currentHero.id,
                victimPosition,
                forEnemy,
                battlefieldHolder)

        if (battlefieldHolder.isGameFinished!!) {
            battlefieldHolder.gameState = GameState.END
        } else {
            battlefieldHolder.incrementStep()
        }

        gameSessionsPool.sendBattlefieldData(battlefieldHolder)
    }

    private fun isSpellValid(currentHero: HeroHolder, spellId: String, playerKey: String): Boolean {
        val castedSpellBelongsToCurrentHero = currentHero.allSpells.stream()
                .filter { spellHolder -> spellHolder.id == spellId }
                .count() > 0
        if (!castedSpellBelongsToCurrentHero) {
            val availableSpellsIds = currentHero.allSpells.map { it.id }.toTypedArray()
            throw Exceptions.produceIllegalArgument(
                    LOG,
                    "Player {0} casted spell {1} that does not belong to current hero. Available spells are {2}",
                    playerKey,
                    spellId,
                    Arrays.toString(availableSpellsIds)
            )
        }
        val castedSpellAvailable = currentHero.availableSpells.stream()
                .filter { spellHolder -> spellHolder.id == spellId }
                .count() > 0
        if (!castedSpellAvailable) {
            val availableSpellsIds = currentHero.availableSpells.map { it.id }
            throw Exceptions.produceIllegalArgument(
                    LOG,
                    "Player {0} casted spell {1} that is not available (on cooldown/suspended/etc). Available spells are {2}",
                    playerKey,
                    spellId,
                    availableSpellsIds.toString()
            )
        }
        return true
    }

    private fun getBattlefieldHolderAndCheckState(session: GameSession, gameState: GameState): BattlefieldHolder {

        if (session.battlefieldHolder == null) {
            throw Exceptions.produceNullPointer(
                    LOG,
                    "User {0} tries to play game without preliminary matchmaking",
                    session.id
            )
        }

        if (gameState != session.battlefieldHolder.gameState) {
            throw Exceptions.produceIllegalState(
                    LOG,
                    "Illegal game state. Expected: {0}, actual: {1}. Initiator: {2}",
                    gameState,
                    session.battlefieldHolder.gameState,
                    session.id
            )
        }

        return session.battlefieldHolder
    }

    companion object {

        private val BATTLEFIELD_HEROES_COUNT = 4
        private val LOG = LoggerFactory.getLogger(GameServiceImpl::class.java)
    }
}