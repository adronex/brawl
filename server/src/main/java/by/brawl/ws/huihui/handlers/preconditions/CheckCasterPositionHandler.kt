package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.SpellsPool
import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.handlers.HuiHandler

class CheckCasterPositionHandler: HuiHandler {

    fun check(spellHolder: SpellHolder, caster: HeroHolder): Boolean {
        val spellConfig = SpellsPool.spellsMap[spellHolder.id]!!
        return spellConfig.casterPositions.any { it == caster.position() }
    }
}