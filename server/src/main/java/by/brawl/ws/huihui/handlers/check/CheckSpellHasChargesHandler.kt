package by.brawl.ws.huihui.handlers.check

import by.brawl.ws.holder.SpellHolder

class CheckSpellHasChargesHandler {

    fun check(spellHolder: SpellHolder): Boolean {
        return spellHolder.charges > 0
    }
}