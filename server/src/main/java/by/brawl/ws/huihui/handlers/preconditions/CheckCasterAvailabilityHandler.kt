package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.effects.EffectType
import by.brawl.ws.huihui.handlers.HuiHandler

class CheckCasterAvailabilityHandler: HuiHandler {

    fun check(caster: HeroHolder): Boolean {
        return caster.effects.any { it.effect.type != EffectType.STUN }
    }
}