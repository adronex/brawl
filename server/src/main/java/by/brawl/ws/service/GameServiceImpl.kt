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
import java.util.*

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
                    "Wrong heroes in battle count. Expected: $BATTLEFIELD_HEROES_COUNT, actual: ${heroesIds.size}"
            )
        }

        val mulliganHeroes = battlefieldHolder.mulliganHeroes[session.id] ?: throw Exceptions.produceIllegalState(LOG, "No heroes for session key ${session.id}")
        val battleHeroes = ArrayList<HeroHolder>()

        for (heroId in heroesIds) {
            val battleHero = mulliganHeroes.find { h -> h.id == heroId } ?:
                    throw Exceptions.produceAccessDenied(
                            LOG,
                            "Player ${session.id} is trying to choose hero with id $heroId from available ids: ${mulliganHeroes.map { it.id }}")

            battleHeroes.add(battleHero)
        }

        battlefieldHolder.addBattleHeroes(session.id, battleHeroes)

        if (battlefieldHolder.isReadyForBattle()) {
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
            throw Exceptions.produceAccessDenied(LOG,"Player $playerKey tries to cast spell in opponents turn")
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

        if (battlefieldHolder.isGameFinished()) {
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
                    "Player $playerKey casted spell $spellId that does not belong to current hero. Available spells are ${Arrays.toString(availableSpellsIds)}"
            )
        }
        val castedSpellAvailable = currentHero.getAvailableSpells().stream()
                .filter { spellHolder -> spellHolder.id == spellId }
                .count() > 0
        if (!castedSpellAvailable) {
            val availableSpellsIds = currentHero.getAvailableSpells().map { it.id }
            throw Exceptions.produceIllegalArgument(
                    LOG,
                    "Player $playerKey casted spell $spellId that is not available (on cooldown/suspended/etc). Available spells are $availableSpellsIds"
            )
        }
        return true
    }

    private fun getBattlefieldHolderAndCheckState(session: GameSession, gameState: GameState): BattlefieldHolder {

        if (gameState != session.battlefieldHolder.gameState) {
            throw Exceptions.produceIllegalState(
                    LOG,
                    "Illegal game state. Expected: $gameState, actual: ${session.battlefieldHolder.gameState}. Initiator: ${session.id}"
            )
        }

        return session.battlefieldHolder
    }

    companion object {

        private val BATTLEFIELD_HEROES_COUNT = 4
        private val LOG = LoggerFactory.getLogger(GameServiceImpl::class.java)
    }

}