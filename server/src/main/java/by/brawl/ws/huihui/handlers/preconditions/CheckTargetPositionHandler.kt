package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.SpellsPool
import by.brawl.ws.huihui.handlers.HuiHandler

class CheckTargetPositionHandler: HuiHandler {

    fun check(spellHolder: SpellHolder, caster: HeroHolder, target: HeroHolder): Boolean {
        val spellConfig = SpellsPool.spellsMap[spellHolder.id]!!
        if (caster.isAllyTo(target)) {
            return spellConfig.targetOwnPositions.any { it == target.position() }
        } else {
            return spellConfig.targetEnemyPositions.any { it == target.position() }
        }
    }
}