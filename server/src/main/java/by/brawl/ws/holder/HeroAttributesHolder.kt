package by.brawl.ws.holder

import by.brawl.entity.bodypart.Bodypart

class HeroAttributesHolder(bodyparts: Set<Bodypart>) {

    val health: AttributeHolder
    val armor: AttributeHolder
    val evasion: AttributeHolder
    val accuracy: AttributeHolder
    val damage: AttributeHolder
    val speed: AttributeHolder
    val criticalDamage: AttributeHolder
    val criticalChance: AttributeHolder

    init {
        var strength = 0
        var agility = 0
        var energy = 0
        for (bodypart in bodyparts) {
            strength += bodypart.strength
            agility += bodypart.agility
            energy += bodypart.energy
        }
        health = AttributeHolder(20)
        armor = AttributeHolder(strength)
        evasion = AttributeHolder(agility)
        accuracy = AttributeHolder(100)
        damage = AttributeHolder(100)
        speed = AttributeHolder(energy)
        criticalDamage = AttributeHolder(200)
        criticalChance = AttributeHolder(15)
    }

}