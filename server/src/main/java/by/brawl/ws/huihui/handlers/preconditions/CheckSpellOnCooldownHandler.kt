package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.handlers.HuiHandler

class CheckSpellOnCooldownHandler: HuiHandler {

    fun check(spellHolder: SpellHolder): Boolean {
        return spellHolder.cooldownTurns == 0
    }
}