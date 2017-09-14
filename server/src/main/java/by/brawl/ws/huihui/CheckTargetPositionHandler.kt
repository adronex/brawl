package by.brawl.ws.huihui

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.service.Spells

class CheckTargetPositionHandler {

    fun check(battlefieldHolder: BattlefieldHolder, spellId: String, targetPosition: Int): Boolean {
        val spellMock: SpellMock = Spells.getSpellById(spellId)
        return spellMock.targetPositions.any { it == targetPosition }
    }
}