package by.brawl.ws.holder

import by.brawl.entity.Hero
import by.brawl.ws.huihui.effects.EffectHolder
import by.brawl.ws.huihui.handlers.CheckEndGameHandler
import java.util.*

class HeroHolder(hero: Hero) {

    val id: String = hero.id
    var allSpells = hero.spells.map { SpellHolder(it) }.toCollection(LinkedHashSet<SpellHolder>())
    var bodyparts = hero.bodyparts.map { BodypartHolder(it) }.toCollection(LinkedHashSet<BodypartHolder>())
    var equipments = hero.equipments.map { EquipmentHolder(it) }.toCollection(LinkedHashSet<EquipmentHolder>())
    val attributes = HeroAttributesHolder(hero.bodyparts)
    val effects = listOf<EffectHolder>()

    fun isAlive() = attributes.health.current > 0
}