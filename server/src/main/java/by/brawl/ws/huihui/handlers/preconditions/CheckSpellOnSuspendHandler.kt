package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.handlers.PreconditionsAbstractHandler

class CheckSpellOnSuspendHandler: PreconditionsAbstractHandler("Spell is on suspend") {

    override fun check(caster: HeroHolder,
                       target: HeroHolder,
                       spellHolder: SpellHolder): Boolean {
        return spellHolder.suspend == 0
    }
}