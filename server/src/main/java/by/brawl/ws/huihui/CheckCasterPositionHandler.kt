package by.brawl.ws.huihui

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.service.Spells

class CheckCasterPositionHandler {

    fun check(battlefieldHolder: BattlefieldHolder, spellId: String, senderId: String): Boolean {
        val heroHolder: HeroHolder = battlefieldHolder.queue.peek()
        val spellMock: SpellMock = Spells.getSpellById(spellId)
        val casterPosition = battlefieldHolder.getHeroPosition(senderId, heroHolder)
        return spellMock.casterPositions.any { it == casterPosition }
    }
}