package by.brawl.ws.huihui.handlers.check

import by.brawl.ws.huihui.conf.SpellConfig

class CheckCasterPositionHandler {

    fun check(spellConfig: SpellConfig, casterPosition: Int): Boolean {
        return spellConfig.casterPositions.any { it == casterPosition }
    }
}