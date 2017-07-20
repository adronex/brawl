package by.brawl.ws.dto

import by.brawl.ws.holder.SpellHolder

class SpellDto(spellHolder: SpellHolder) {

    val id = spellHolder.id
    val cooldownTurns = spellHolder.cooldownTurns
    val suspendTurns = spellHolder.suspendTurns

}