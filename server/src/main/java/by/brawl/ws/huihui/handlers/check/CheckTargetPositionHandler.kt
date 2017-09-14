package by.brawl.ws.huihui.handlers.check

import by.brawl.ws.huihui.conf.SpellConfig

class CheckTargetPositionHandler {

    fun check(spellConfig: SpellConfig, targetPosition: Int): Boolean {
        return spellConfig.targetPositions.any { it == targetPosition }
    }
}