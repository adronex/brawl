package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.effects.EffectType
import by.brawl.ws.huihui.handlers.PreconditionsAbstractHandler

class CheckCasterAvailabilityHandler: PreconditionsAbstractHandler("Caster is not available (under stun etc)") {

    override fun check(caster: HeroHolder,
                       target: HeroHolder,
                       spellHolder: SpellHolder): Boolean {
        return !caster.effects.any { it.effect.type == EffectType.STUN }
    }
}