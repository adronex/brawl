package by.brawl.ws.holder

import by.brawl.entity.Hero
import java.util.*

class HeroHolder(hero: Hero) {

    val id: String = hero.id
    var allSpells = hero.spells.map { SpellHolder(it) }.toCollection(LinkedHashSet<SpellHolder>())
    var bodyparts = hero.bodyparts.map { BodypartHolder(it) }.toCollection(LinkedHashSet<BodypartHolder>())
    var equipments = hero.equipments.map { EquipmentHolder(it) }.toCollection(LinkedHashSet<EquipmentHolder>())
    val attributes = HeroAttributesHolder(hero.bodyparts)

    val availableSpells = allSpells.filter { it.isAvailable }

    val isAlive = attributes.health.current > 0

    fun heal(value: Int) {
        attributes.health.incrementCurrent(value)
    }

    fun hit(value: Int) {
        attributes.health.decrementCurrent(value)
    }

}