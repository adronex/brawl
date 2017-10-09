package by.brawl.ws.huihui

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.conf.ImpactType
import by.brawl.ws.huihui.conf.IntegerImpactConfig
import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.handlers.HandlersPool

class SpellInstance(private val config: SpellConfig) {
    private val handlersPool = HandlersPool()
    // todo: make it work
    private val impactsMap = mutableMapOf<HeroHolder, List<IntegerImpactConfig>>()

    fun build(spellId: String, position: Int, battlefieldHolder: BattlefieldHolder) {
//        battlefieldHolder.get
    }

    fun checkPreConditions(caster: HeroHolder, target: HeroHolder, spellHolder: SpellHolder) {
//        handlersPool.checkCasterAvailabilityHandler.check(caster)
//        handlersPool.checkCasterPositionHandler.check(config)
//        handlersPool.checkSpellHasChargesHandler.check(spellHolder)
//        handlersPool.checkSpellOnCooldownHandler.check(spellHolder)
//        handlersPool.checkSpellOnSuspendHandler.check(spellHolder)
//        handlersPool.checkTargetAvailabilityHandler.check(target)
//        handlersPool.checkTargetPositionHandler.check(config)
    }

    fun calculate() {
//        val casterImpacts = config.casterIntegerImpacts.map { it. } IntegerImpact(ImpactType.DAMAGE, config.targetIntegerImpacts[0].calculateValue())
//        val impacts = listOf<Impact<Any>>()
//        impactsMap.put(hero, impacts)
    }

    fun apply() {
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