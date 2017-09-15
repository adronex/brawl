package by.brawl.ws.holder

import by.brawl.entity.Hero
import by.brawl.ws.huihui.effects.EffectHolder
import java.util.*

class HeroHolder(hero: Hero) {

    val id: String = hero.id
    var allSpells = hero.spells.map { SpellHolder(it) }.toList()
    var bodyparts = hero.bodyparts.map { BodypartHolder(it) }.toCollection(LinkedHashSet<BodypartHolder>())
    var equipments = hero.equipments.map { EquipmentHolder(it) }.toCollection(LinkedHashSet<EquipmentHolder>())
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
}