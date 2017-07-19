package by.brawl.ws.dto

import by.brawl.entity.bodypart.EquipmentCategory
import by.brawl.entity.bodypart.EquipmentType
import by.brawl.ws.holder.EquipmentHolder

class EquipmentDto(equipment: EquipmentHolder) {

    val name = equipment.name
    val category = equipment.category
    val type = equipment.type

}
