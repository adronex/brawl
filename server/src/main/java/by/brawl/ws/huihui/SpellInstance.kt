package by.brawl.ws.huihui

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.effects.EffectHolder
import by.brawl.ws.huihui.impact.Impact
import by.brawl.ws.huihui.impact.ImpactType

class SpellInstance (){
    // todo: make it work
    val impacts: Map<HeroHolder, List<Impact<Any>>> = mapOf()

    fun apply() {
        impacts.forEach{ hero, impacts ->
            run {
                impacts.forEach { impact ->
                    when (impact.getType()) {
                        ImpactType.DAMAGE -> hero.hit(impact.getValue() as Int)
                        ImpactType.HEAL -> hero.heal(impact.getValue() as Int)
                        ImpactType.BARRIER -> hero.barrier(impact.getValue() as Int)
                        //todo: ImpactType.MOVE
                        ImpactType.EFFECT -> hero.effects.add(impact.getValue() as EffectHolder)
                    }
                }
            }
        }
    }
}