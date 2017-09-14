package by.brawl.ws.service

import by.brawl.util.Exceptions
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.conf.SpellConfig
import by.brawl.ws.spell.SpellLogic
import by.brawl.ws.spell.Uppercut
import org.reflections.Reflections
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class Spells {

    private val spellsMap = HashMap<String, SpellLogic>()

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


    companion object {

        private val LOG = LoggerFactory.getLogger(Spells::class.java)
        private val spellsConfigs = HashMap<String, SpellConfig>()

        init {
            val uppercut = SpellConfig(
                    id = "UPPERCUT",
                    casterPositions = listOf(1, 2),
                    logic = { battlefieldHolder, heroHolder -> Uppercut.newCast(battlefieldHolder, heroHolder) }
            )
            spellsConfigs.put(uppercut.id, uppercut)
        }

        fun getSpellById(spellId: String): SpellConfig = spellsConfigs[spellId]
                ?: throw Exceptions.produceIllegalState(LOG, "Spell with id $spellId is absent in spell pool")
    }
}