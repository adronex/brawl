package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.effects.EffectType

class CheckTargetAvailabilityHandler {

    fun check(target: HeroHolder): Boolean {
        return target.effects.any { it.effect.type != EffectType.INVISIBILITY }
    }
}