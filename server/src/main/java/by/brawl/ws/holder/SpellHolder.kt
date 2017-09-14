package by.brawl.ws.holder

import by.brawl.entity.Spell

class SpellHolder(spell: Spell) {

    val id: String = spell.id
    val cooldownTurns: Int = 0
    val suspendTurns: Int = 0
    val charges: Int = 0

}