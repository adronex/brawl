package by.brawl.ws.huihui.handlers

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.holder.SpellHolder

abstract class PreconditionsAbstractHandler(private val errorMessage: String) {

    fun handle(caster: HeroHolder,
               target: HeroHolder,
               spellHolder: SpellHolder) {
        if (!check(caster, target, spellHolder)) throw IllegalArgumentException(errorMessage)
    }

    protected abstract fun check(caster: HeroHolder,
                       target: HeroHolder,
                       spellHolder: SpellHolder): Boolean
}