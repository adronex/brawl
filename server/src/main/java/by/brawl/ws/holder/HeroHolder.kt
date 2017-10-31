package by.brawl.ws.holder

import by.brawl.entity.Hero
import by.brawl.ws.huihui.SpellsPool
import by.brawl.ws.huihui.effects.EffectHolder
import java.util.*

class HeroHolder(hero: Hero,
                 private val squadHolder: SquadHolder) {

    val id: String = hero.id!!
    val name: String = hero.name
    val allSpells = hero.spells.map {
        SpellHolder(SpellsPool.spellsMap[it.id]
                ?: throw IllegalStateException("Spell with id ${it.id} isn't present in spell pool."))
    }.toList()
    val bodyparts = hero.bodyparts.map { BodypartHolder(it) }.toCollection(LinkedHashSet<BodypartHolder>())
    val equipments = hero.equipments.map { EquipmentHolder(it) }.toCollection(LinkedHashSet<EquipmentHolder>())
    val attributes = HeroAttributesHolder(hero.bodyparts)
    val effects = mutableListOf<EffectHolder>()

    fun isAlive() = attributes.health.current > 0

    fun heal(value: Int) {
        attributes.health.incrementCurrent(value)
    }

    fun hit(value: Int) {
        if (value > attributes.barrier.current) {
            val delta = value - attributes.barrier.current
            attributes.barrier.decrementCurrent(delta)
            attributes.health.decrementCurrent(value - delta)
        } else {
            attributes.barrier.decrementCurrent(value)
        }
    }

    fun barrier(value: Int) {
        attributes.barrier.incrementCurrent(value)
    }

    fun position(): Int = squadHolder.heroes.indexOf(this)

    fun move(value: Int) {
        val oldPosition = position()
        var newPosition = oldPosition + value
        if (newPosition < 0) {
            newPosition = 0
        } else if (newPosition > squadHolder.heroes.size - 1) {
            newPosition = squadHolder.heroes.size - 1
        }
        if (oldPosition <= newPosition) {
            Collections.rotate(squadHolder.heroes.subList(oldPosition, newPosition + 1), -1);
        } else {
            Collections.rotate(squadHolder.heroes.subList(oldPosition, newPosition + 1), 1);
        }
    }
}