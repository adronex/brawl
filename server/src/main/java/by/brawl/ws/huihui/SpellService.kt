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
             spellId: String,
             clickedHeroPosition: Int) {
        val battlefieldHolder = gameSession.battlefieldHolder
        val caster: HeroHolder = battlefieldHolder.getFirstHeroFromQueue()
        // todo: check current turn availability
        val target: HeroHolder = battlefieldHolder.getHeroByPosition(gameSession, clickedHeroPosition)
        val spellHolder: SpellHolder = caster.allSpells.find { it.id == spellId } ?: throw IllegalArgumentException("Spell with id $spellId doesn't belong to caster")
        // todo: check spell hero-owner
        val config: SpellConfig = SpellsPool.spellsMap[spellHolder.id]
                ?: throw IllegalArgumentException("Spell with id ${spellHolder.id} doesn't exist!")
        checkPreConditions(caster, target, spellHolder, config)
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
                                   spellConfig: SpellConfig): Boolean {
        if (!preconditionsPool.checkCasterAvailabilityHandler.check(caster)) {
            throw IllegalStateException("Caster is not available (under stun etc)")
        }
        if (!preconditionsPool.checkCasterPositionHandler.check(spellConfig, caster.position())) {
            throw IllegalStateException("Caster position check failed")
        }
        if (!preconditionsPool.checkSpellHasChargesHandler.check(spellHolder)) {
            throw IllegalStateException("Spell is out of charges")
        }
        if (!preconditionsPool.checkSpellOnCooldownHandler.check(spellHolder)) {
            throw IllegalStateException("Spell is on cooldown")
        }
        if (!preconditionsPool.checkSpellOnSuspendHandler.check(spellHolder)) {
            throw IllegalStateException("Spell is on suspend")
        }
        if (!preconditionsPool.checkTargetAvailabilityHandler.check(target)) {
            throw IllegalStateException("Target is not available (invisible etc)")
        }
        if (!preconditionsPool.checkTargetPositionHandler.check(spellConfig, target.position())) {
            throw IllegalStateException("Incorrect target position")
        }
        return true
    }

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
}