package by.brawl.ws.service

import by.brawl.entity.Squad
import by.brawl.util.Exceptions
import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.GameState
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.holder.gamesession.GameSession
import by.brawl.ws.holder.gamesession.GameSessionsPool
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class GameServiceImpl constructor(private val spellCastService: SpellCastService,
                                           private val gameSessionsPool: GameSessionsPool) : GameService {

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

    override fun castSpell(session: GameSession, spellPosition: Int, targetPosition: Int) {

        val caster: HeroHolder = session.battlefieldHolder.getFirstHeroFromQueue()
        val casterPosition = session.battlefieldHolder.getPositionOfHero(caster)
        val targetIsEnemy = targetPosition > 0
        val target: HeroHolder = session.battlefieldHolder.getHeroByPosition(session, targetPosition, targetIsEnemy )
        val spellHolder: SpellHolder = caster.allSpells[spellPosition]
    }

    // todo: old function, take from it all that necessary
    fun oldcast(session: GameSession, spellPosition: Int, targetPosition: Int) {

        val sourceBattlefieldHolder = getBattlefieldHolderAndCheckState(session, GameState.PLAYING)
        val playerKey = session.id
        val currentHero = sourceBattlefieldHolder.queue.element()

//        if (!isSpellValid(sourceBattlefieldHolder, currentHero, spellId, playerKey)) {
//            return
//        }

        var affectedBattlefieldHolder = sourceBattlefieldHolder
//        affectedBattlefieldHolder = spellCastService.castSpell(spellId,
//                session.id,
//                currentHero.id,
//                victimPosition,
//                forEnemy,
//                affectedBattlefieldHolder)

        if (affectedBattlefieldHolder.isGameFinished()) {
            affectedBattlefieldHolder.gameState = GameState.END
        } else {
            affectedBattlefieldHolder.incrementStep()
        }

        gameSessionsPool.sendBattlefieldData(affectedBattlefieldHolder)

    }
    // is current turn belongs to requester?
    // is current hero has requested spell?
    // is current spell available (not on suspend/cooldown)?
//    private fun isSpellValid(battlefieldHolder: BattlefieldHolder, currentHero: HeroHolder, spellId: String, playerKey: String): Boolean {
//        isSpellWasCastedInCorrectTurn(battlefieldHolder, playerKey, currentHero)
//        val castedSpellBelongsToCurrentHero = currentHero.allSpells.stream()
//                .filter { spellHolder -> spellHolder.id == spellId }
//                .count() > 0
//        if (!castedSpellBelongsToCurrentHero) {
//            val availableSpellsIds = currentHero.allSpells.map { it.id }.toTypedArray()
//            throw Exceptions.produceIllegalArgument(
//                    LOG,
//                    "Player $playerKey casted spell $spellId that does not belong to current hero. Available spells are ${Arrays.toString(availableSpellsIds)}"
//            )
//        }
//        val castedSpellAvailable = currentHero.getAvailableSpells().stream()
//                .filter { spellHolder -> spellHolder.id == spellId }
//                .count() > 0
//        if (!castedSpellAvailable) {
//            val availableSpellsIds = currentHero.getAvailableSpells().map { it.id }
//            throw Exceptions.produceIllegalArgument(
//                    LOG,
//                    "Player $playerKey casted spell $spellId that is not available (on cooldown/suspended/etc). Available spells are $availableSpellsIds"
//            )
//        }
//        return true
//    }

    private fun isSpellWasCastedInCorrectTurn(battlefieldHolder: BattlefieldHolder, playerKey: String, currentHero: HeroHolder): Boolean {
        if (!battlefieldHolder.getBattleHeroes(playerKey, false).contains(currentHero)) {
            throw Exceptions.produceAccessDenied(LOG,"Player $playerKey tries to cast spell in opponents turn")
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