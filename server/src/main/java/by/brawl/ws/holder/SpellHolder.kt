package by.brawl.ws.holder

import by.brawl.entity.Spell

class SpellHolder(spell: Spell) {
    val id: String = spell.id
    val cooldownTurns: Int = 0
    val suspendTurns: Int = 0

    val isAvailable = cooldownTurns == 0 && suspendTurns == 0
}
