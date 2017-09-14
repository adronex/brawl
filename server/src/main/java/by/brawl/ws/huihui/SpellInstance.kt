package by.brawl.ws.huihui

import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.huihui.impact.Impact

class SpellInstance (spellConfig: SpellConfig){
    val impacts: Map<HeroHolder, List<Impact>>
}