package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.effects.EffectType
import by.brawl.ws.huihui.handlers.PreconditionsAbstractHandler

class CheckTargetAvailabilityHandler: PreconditionsAbstractHandler("Target is not available (invisible etc)") {

    override fun check(caster: HeroHolder,
                       target: HeroHolder,
                       spellHolder: SpellHolder): Boolean {
        return target.effects.any { it.effect.type == EffectType.INVISIBILITY }
    }
}