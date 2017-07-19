package by.brawl.ws.dto

import by.brawl.ws.holder.HeroHolder
import java.util.*

class HeroDto(heroHolder: HeroHolder) {
    val id: String = heroHolder.id
    var bodyparts = heroHolder.bodyparts.map { BodypartDto(it) }.toCollection(LinkedHashSet<BodypartDto>())
    var equipments = heroHolder.equipments.map { EquipmentDto(it) }.toCollection(LinkedHashSet<EquipmentDto>())
    val attributes = heroHolder.attributes
}
