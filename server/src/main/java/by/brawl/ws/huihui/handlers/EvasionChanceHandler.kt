package by.brawl.ws.huihui.handlers

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder
import java.util.*

class EvasionChanceHandler {

    private val random = Random()

    fun check(caster: HeroHolder, target: HeroHolder, spellHolder: SpellHolder): Boolean {
        return target.attributes.evasion.current > random.nextInt(100 + 1)
    }
}