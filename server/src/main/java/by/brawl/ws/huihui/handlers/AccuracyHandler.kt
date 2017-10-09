package by.brawl.ws.huihui.handlers

import by.brawl.ws.holder.HeroHolder
import java.util.*

class AccuracyHandler: HuiHandler {

    private val random = Random()

    fun check(caster: HeroHolder): Boolean {
        return caster.attributes.accuracy.current > random.nextInt(100 + 1)
    }
}