package by.brawl.ws.huihui

import by.brawl.ws.huihui.conf.SpellConfig
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class SpellsPool {

    companion object {
        val spellsMap: Map<String, SpellConfig>
        init {
            val tempMap = mutableMapOf<String, SpellConfig>()
            val mapper = jacksonObjectMapper()
            val source = this::class.java.getResource("/conf/spells-old.json").readText()
            mapper.readTree(source)?.forEach {
                val id: String = it.get("id").textValue()
//                val casterPositions: List<Int> = it.get("casterPositions").map{ it.intValue() }
//                val targetPositions: List<Int> = it.get("targetPositions").map{ it.intValue() }
                tempMap.put(id, SpellConfig(id))
            }
            spellsMap = tempMap
        }
    }
}