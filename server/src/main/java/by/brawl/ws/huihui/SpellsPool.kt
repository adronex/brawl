package by.brawl.ws.huihui

import by.brawl.ws.huihui.conf.SpellConfig

class SpellsPool {

    companion object {
        val spellsMap: Map<String, SpellConfig>
        init {
            val tempMap = mutableMapOf<String, SpellConfig>()
            // todo: parse from configs
            tempMap.put("1", SpellConfig("1"))
            tempMap.put("2", SpellConfig("2"))
            tempMap.put("3", SpellConfig("3"))
            tempMap.put("4", SpellConfig("4"))
            tempMap.put("5", SpellConfig("5"))
            tempMap.put("6", SpellConfig("6"))
            tempMap.put("7", SpellConfig("7"))
            tempMap.put("8", SpellConfig("8"))
            spellsMap = tempMap
        }
    }
}