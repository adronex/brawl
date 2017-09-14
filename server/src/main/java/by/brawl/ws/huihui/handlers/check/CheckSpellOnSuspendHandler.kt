package by.brawl.ws.huihui.handlers.check

import by.brawl.ws.holder.SpellHolder

class CheckSpellOnSuspendHandler {

    fun check(spellHolder: SpellHolder): Boolean {
        return spellHolder.suspendTurns == 0
    }
}