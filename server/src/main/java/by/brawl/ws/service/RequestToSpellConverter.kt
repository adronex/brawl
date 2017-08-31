package by.brawl.ws.service

import by.brawl.util.Exceptions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RequestToSpellConverter
constructor(private val spells: Spells) {

    fun convert(spellId: String,
                victimPosition: Int?,
                forEnemy: Boolean?) {
        val castedSpell = spells.spellsMap.get(spellId)
                ?: throw Exceptions.produceNullPointer(LOG, "Casted spell with id $spellId is absent in spell pool")

        castedSpell.targetable
        castedSpell.enemyTargets.contains(victimPosition)
        // check targets
        // roll values
        // affect battlefield
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(RequestToSpellConverter::class.java)
    }
}