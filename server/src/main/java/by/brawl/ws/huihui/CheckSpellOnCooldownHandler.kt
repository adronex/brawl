package by.brawl.ws.huihui

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder

class CheckSpellOnCooldownHandler {

    fun check(battlefieldHolder: BattlefieldHolder, spellId: String, senderId: String): Boolean {
        val heroHolder: HeroHolder = battlefieldHolder.queue.peek()
        val spellHolder: SpellHolder = heroHolder.allSpells.first { it.id == spellId }

        return spellHolder.cooldownTurns == 0
    }
}