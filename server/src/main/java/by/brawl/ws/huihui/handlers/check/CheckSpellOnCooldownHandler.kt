package by.brawl.ws.huihui.handlers.check

import by.brawl.ws.holder.SpellHolder

class CheckSpellOnCooldownHandler {

    fun check(spellHolder: SpellHolder): Boolean {
        return spellHolder.cooldownTurns == 0
    }
}