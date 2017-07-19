package by.brawl.entity.bodypart

import by.brawl.entity.NamedEntity

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Equipment : NamedEntity() {

    @Enumerated(EnumType.STRING)
    val type: EquipmentType? = null
    @Enumerated(EnumType.STRING)
    val category: EquipmentCategory? = null
    val requiredStrength: Int? = null
    val requiredAgility: Int? = null
    val requiredEnergy: Int? = null
}
