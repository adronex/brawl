package by.brawl.ws.service

import by.brawl.ws.huihui.SpellConfig
import by.brawl.ws.spell.SpellLogic
import org.reflections.Reflections
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class Spells {

    val spellsMap = HashMap<String, SpellLogic>()

    @PostConstruct
    @Throws(InstantiationException::class, IllegalAccessException::class)
    fun init() {

        findAllSpellsLogic(SpellLogic::class.java.`package`.name)
    }

    @Throws(IllegalAccessException::class, InstantiationException::class)
    private fun findAllSpellsLogic(scanPackage: String) {

        val reflections = Reflections(scanPackage)

        val allClasses = reflections.getSubTypesOf(SpellLogic::class.java)
        for (spellLogicClass in allClasses) {
            val spellLogic = spellLogicClass.newInstance()
            spellsMap.put(spellLogic.id, spellLogic)
        }
    }

    val spellConfig = SpellConfig("Yes", false, 0, false)
}