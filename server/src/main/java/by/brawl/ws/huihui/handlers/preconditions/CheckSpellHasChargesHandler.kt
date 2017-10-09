package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.handlers.HuiHandler

class CheckSpellHasChargesHandler: HuiHandler {

    fun check(spellHolder: SpellHolder): Boolean {
        return spellHolder.charges > 0
    }
}