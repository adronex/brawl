package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.handlers.PreconditionsAbstractHandler

class CheckSpellHasChargesHandler: PreconditionsAbstractHandler("Spell is out of charges") {

    override fun check(caster: HeroHolder,
                       target: HeroHolder,
                       spellHolder: SpellHolder): Boolean {
        return !spellHolder.config.chargeable || spellHolder.charges > 0
    }
}