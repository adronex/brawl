package by.brawl.ws.holder

import by.brawl.entity.bodypart.Equipment
import by.brawl.entity.bodypart.EquipmentCategory
import by.brawl.entity.bodypart.EquipmentType

class EquipmentHolder(equipment: Equipment) {
    val id: String? = equipment.id
    val name = equipment.name
    val type = equipment.type
    val category = equipment.category
}
