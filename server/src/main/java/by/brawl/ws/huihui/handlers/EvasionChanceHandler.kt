package by.brawl.ws.huihui.handlers

import by.brawl.ws.holder.HeroHolder
import java.util.*

class EvasionChanceHandler: HuiHandler {

    private val random = Random()

    fun check(target: HeroHolder): Boolean {
        return target.attributes.evasion.current > random.nextInt(100 + 1)
    }
}