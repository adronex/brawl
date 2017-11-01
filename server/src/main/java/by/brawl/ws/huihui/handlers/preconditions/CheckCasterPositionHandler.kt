package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.SpellsPool
import by.brawl.ws.huihui.handlers.PreconditionsAbstractHandler

class CheckCasterPositionHandler: PreconditionsAbstractHandler("Caster position check failed") {

    override fun check(caster: HeroHolder,
                       target: HeroHolder,
                       spellHolder: SpellHolder): Boolean {
        val spellConfig = SpellsPool.spellsMap[spellHolder.id]!!
        return spellConfig.casterPositions.any { it == caster.position() }
    }
}