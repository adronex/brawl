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

class SpellService() {

    private val spellsPool = SpellsPool()
    private val preconditionHandlersPool = HandlersPool()
    private val checkEndGameHandler = CheckEndGameHandler()
    //  private val impactsMap = mutableMapOf<HeroHolder, List<IntegerImpactConfig>>()

    fun cast(clickedSpellPosition: Int,
             clickedHeroPosition: Int,
             gameSession: GameSession) {
        val battlefieldHolder = gameSession.battlefieldHolder
        val caster: HeroHolder = battlefieldHolder.getFirstHeroFromQueue()
        // todo: check current turn availability
        val target: HeroHolder = battlefieldHolder.getHeroByPosition(gameSession, clickedHeroPosition)
        val spellHolder: SpellHolder = caster.allSpells[clickedSpellPosition]
        // todo: check spell hero-owner
        val config: SpellConfig = spellsPool.spellsMap[spellHolder.id]
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
            preconditionHandlersPool.checkCasterAvailabilityHandler.check(caster)
                    && preconditionHandlersPool.checkCasterPositionHandler.check(spellConfig, caster.position())
                    && preconditionHandlersPool.checkSpellHasChargesHandler.check(spellHolder)
                    && preconditionHandlersPool.checkSpellOnCooldownHandler.check(spellHolder)
                    && preconditionHandlersPool.checkSpellOnSuspendHandler.check(spellHolder)
                    && preconditionHandlersPool.checkTargetAvailabilityHandler.check(target)
                    && preconditionHandlersPool.checkTargetPositionHandler.check(spellConfig, target.position())

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