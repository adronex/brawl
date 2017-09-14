package by.brawl.ws.huihui.handlers.check

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.effects.EffectType

class CheckTargetAvailabilityHandler {

    fun check(heroHolder: HeroHolder): Boolean {
        return heroHolder.effects.any { it.effect.type != EffectType.INVISIBILITY }
    }
}