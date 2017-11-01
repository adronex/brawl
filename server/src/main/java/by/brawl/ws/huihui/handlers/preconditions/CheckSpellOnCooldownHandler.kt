package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.handlers.PreconditionsAbstractHandler

class CheckSpellOnCooldownHandler: PreconditionsAbstractHandler("Spell is on cooldown") {

    override fun check(caster: HeroHolder,
                       target: HeroHolder,
                       spellHolder: SpellHolder): Boolean {
        return spellHolder.cooldown == 0
    }
}