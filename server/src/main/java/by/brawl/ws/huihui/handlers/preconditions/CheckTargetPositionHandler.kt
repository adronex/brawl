package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import by.brawl.ws.huihui.SpellsPool
import by.brawl.ws.huihui.handlers.PreconditionsAbstractHandler

class CheckTargetPositionHandler: PreconditionsAbstractHandler("Incorrect target position") {

    override fun check(caster: HeroHolder,
                       target: HeroHolder,
                       spellHolder: SpellHolder): Boolean {
        val spellConfig = SpellsPool.spellsMap[spellHolder.id]!!
        return if (caster.isAllyTo(target)) {
            spellConfig.targetOwnPositions.any { it == target.position() }
        } else {
            spellConfig.targetEnemyPositions.any { it == target.position() }
        }
    }
}