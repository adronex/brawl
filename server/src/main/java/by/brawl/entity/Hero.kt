package by.brawl.entity

import by.brawl.entity.bodypart.Bodypart
import by.brawl.entity.bodypart.Equipment
import java.util.*
import javax.persistence.*

@Entity
class Hero : NamedEntity() {

    @ManyToOne
    lateinit var owner: Account

    @ManyToMany
    @JoinTable(
            name = "hero_spell",
            joinColumns = arrayOf(JoinColumn(name = "hero_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "spell_id")))
    val spells: Set<Spell> = LinkedHashSet()

    @ManyToMany
    @JoinTable(
            name = "hero_bodypart",
            joinColumns = arrayOf(JoinColumn(name = "hero_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "bodypart_id")))
    val bodyparts: Set<Bodypart> = LinkedHashSet()

    @ManyToMany
    @JoinTable(
            name = "hero_equipment",
            joinColumns = arrayOf(JoinColumn(name = "hero_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "equipment_id")))
    val equipments: Set<Equipment> = LinkedHashSet()

    override fun toString(): String {
        return "Hero{" +
                "name='" + name + '\'' +
                ", owner=" + owner.username +
                '}'
    }

}