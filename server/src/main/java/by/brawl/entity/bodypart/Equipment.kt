package by.brawl.entity.bodypart

import by.brawl.entity.NamedEntity

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Equipment : NamedEntity() {

    @Enumerated(EnumType.STRING)
    val type = EquipmentType.UNKNOWN
    @Enumerated(EnumType.STRING)
    val category = EquipmentCategory.UNKNOWN
    val requiredStrength = 0
    val requiredAgility = 0
    val requiredEnergy = 0

}