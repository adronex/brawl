package by.brawl.ws.huihui.handlers

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.handlers.check.*

class TemplateHandler {
    val checkCasterAvailabilityHandler = CheckCasterAvailabilityHandler()
    val checkCasterPositionHandler = CheckCasterPositionHandler()
    val checkSpellHasChargesHandler = CheckSpellHasChargesHandler()
    val checkSpellOnCooldownHandler = CheckSpellOnCooldownHandler()
    val checkSpellOnSuspendHandler = CheckSpellOnSuspendHandler()
    val checkTargetAvailabilityHandler = CheckTargetAvailabilityHandler()
    val checkTargetPositionHandler = CheckTargetPositionHandler()

    fun checkIfSpellCanBeCasted(spellConfig: SpellConfig,
                                spellHolder: SpellHolder,
                                caster: HeroHolder,
                                target: HeroHolder,
                                casterPosition: Int,
                                targetPosition: Int) {
        checkCasterAvailabilityHandler.check(caster)
        checkCasterPositionHandler.check(spellConfig, casterPosition)
        checkSpellHasChargesHandler.check(spellHolder)
        checkSpellOnCooldownHandler.check(spellHolder)
        checkSpellOnSuspendHandler.check(spellHolder)
        checkTargetAvailabilityHandler.check(target)
        checkTargetPositionHandler.check(spellConfig, targetPosition)
    }
}