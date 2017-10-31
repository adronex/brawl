package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.SpellsPool
import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.handlers.HuiHandler

class CheckTargetPositionHandler: HuiHandler {

    fun check(spellHolder: SpellHolder, target: HeroHolder): Boolean {
        val spellConfig = SpellsPool.spellsMap[spellHolder.id]!!
        return spellConfig.targetPositions.any { it == target.position() }
    }
}