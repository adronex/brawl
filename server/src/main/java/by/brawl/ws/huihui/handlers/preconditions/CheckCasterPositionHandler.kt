package by.brawl.ws.huihui.handlers.preconditions

import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.handlers.HuiHandler

class CheckCasterPositionHandler: HuiHandler {

    fun check(spellConfig: SpellConfig, casterPosition: Int): Boolean {
        return spellConfig.casterPositions.any { it == casterPosition }
    }
}