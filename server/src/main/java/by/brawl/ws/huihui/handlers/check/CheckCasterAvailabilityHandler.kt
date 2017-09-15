package by.brawl.ws.huihui.handlers.check

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.effects.EffectType

class CheckCasterAvailabilityHandler {

    fun check(caster: HeroHolder): Boolean {
        return caster.effects.any { it.effect.type != EffectType.STUN }
    }
}