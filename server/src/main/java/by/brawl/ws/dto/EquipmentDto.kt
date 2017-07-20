package by.brawl.ws.dto

import by.brawl.ws.holder.EquipmentHolder

class EquipmentDto(equipment: EquipmentHolder) {

    val name = equipment.name
    val category = equipment.category
    val type = equipment.type

}