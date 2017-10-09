package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.handlers.HuiHandler

class CheckTargetPositionHandler: HuiHandler {

    fun check(spellConfig: SpellConfig, targetPosition: Int): Boolean {
        return spellConfig.targetPositions.any { it == targetPosition }
    }
}