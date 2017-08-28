package by.brawl.entity.bodypart

import by.brawl.entity.IdEntity

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Bodypart : IdEntity() {

    val name = "UNKNOWN"
    @Enumerated(EnumType.STRING)
    val type = BodypartType.UNKNOWN
    val strength = 0
    val agility = 0
    val energy = 0

}