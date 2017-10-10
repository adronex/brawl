package by.brawl.ws.huihui

import by.brawl.util.Exceptions
import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.holder.gamesession.GameSession
import by.brawl.ws.huihui.conf.ImpactType
import by.brawl.ws.huihui.conf.IntegerImpactConfig
import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.handlers.CheckEndGameHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SpellService(private val preconditionsPool: HandlersPool,
                   private val checkEndGameHandler: CheckEndGameHandler) {

    fun cast(gameSession: GameSession,
             clickedSpellPosition: Int,
             clickedHeroPosition: Int) {
        val battlefieldHolder = gameSession.battlefieldHolder
        val caster: HeroHolder = battlefieldHolder.getFirstHeroFromQueue()
        // todo: check current turn availability
        val target: HeroHolder = battlefieldHolder.getHeroByPosition(gameSession, clickedHeroPosition)
        val spellHolder: SpellHolder = caster.allSpells[clickedSpellPosition]
        // todo: check spell hero-owner
        val config: SpellConfig = SpellsPool.spellsMap[spellHolder.id]
                ?: throw Exceptions.produceIllegalArgument(LOG, "Spell with id ${spellHolder.id} doesn't exist!")
        val preconditionPassed = checkPreConditions(caster, target, spellHolder, config)
        if (!preconditionPassed) {
            throw Exceptions.produceIllegalState(LOG, "Preconditions check failed!")
        }
        // BUILDING IMPACTS MAP
        val impactsMap = mutableMapOf<HeroHolder, List<IntegerImpactConfig>>()
        impactsMap.put(caster, config.casterIntegerImpacts)
        impactsMap.put(target, config.targetIntegerImpacts)
        config.additionalIntegerImpacts.forEach { position, impacts ->
            run {
                val hero = battlefieldHolder.getHeroByPosition(gameSession, position)
                impactsMap.put(hero, impacts)
            }
        }
        // todo: check resists and evasions
        apply(impactsMap)
        spellHolder.onCast()
        checkEndGameHandler.check(battlefieldHolder)
        // todo: run endTurnHandler
        // todo: run startTurnHandler
    }

    private fun checkPreConditions(caster: HeroHolder,
                                   target: HeroHolder,
                                   spellHolder: SpellHolder,
                                   spellConfig: SpellConfig): Boolean =
            preconditionsPool.checkCasterAvailabilityHandler.check(caster)
                    && preconditionsPool.checkCasterPositionHandler.check(spellConfig, caster.position())
                    && preconditionsPool.checkSpellHasChargesHandler.check(spellHolder)
                    && preconditionsPool.checkSpellOnCooldownHandler.check(spellHolder)
                    && preconditionsPool.checkSpellOnSuspendHandler.check(spellHolder)
                    && preconditionsPool.checkTargetAvailabilityHandler.check(target)
                    && preconditionsPool.checkTargetPositionHandler.check(spellConfig, target.position())

    private fun apply(impactsMap: Map<HeroHolder, List<IntegerImpactConfig>>) {
        impactsMap.forEach { hero, impacts ->
            run {
                impacts.forEach { impact ->
                    when (impact.getType()) {
                        ImpactType.DAMAGE -> hero.hit(impact.calculateValue())
                        ImpactType.HEAL -> hero.heal(impact.calculateValue())
                        ImpactType.BARRIER -> hero.barrier(impact.calculateValue())
                        ImpactType.MOVE -> hero.move(impact.calculateValue())
                    //                 ImpactType.EFFECT -> hero.effects.add(impact.calculateValue() as EffectHolder)
                    }
                }
            }
        }
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(BattlefieldHolder::class.java)
    }
}