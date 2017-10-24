package by.brawl.entity

import by.brawl.entity.bodypart.Bodypart
import by.brawl.entity.bodypart.Equipment
import java.util.*
import javax.persistence.*

@Entity
class Hero(id: String?,
           name: String,
           @ManyToOne val owner: Account,

           @ManyToMany
           @JoinTable(
                   name = "hero_spell",
                   joinColumns = arrayOf(JoinColumn(name = "hero_id")),
                   inverseJoinColumns = arrayOf(JoinColumn(name = "spell_id")))
           val spells: Set<Spell> = LinkedHashSet(),

           @ManyToMany
           @JoinTable(
                   name = "hero_bodypart",
                   joinColumns = arrayOf(JoinColumn(name = "hero_id")),
                   inverseJoinColumns = arrayOf(JoinColumn(name = "bodypart_id")))
           val bodyparts: Set<Bodypart> = LinkedHashSet(),

           @ManyToMany
           @JoinTable(
                   name = "hero_equipment",
                   joinColumns = arrayOf(JoinColumn(name = "hero_id")),
                   inverseJoinColumns = arrayOf(JoinColumn(name = "equipment_id")))
           val equipments: Set<Equipment> = LinkedHashSet())
    : NamedEntity(id, name) {

    private constructor(): this(id = null, name = "Default", owner = Account())

    override fun toString(): String {
        return "Hero(owner=$owner, spells=$spells, bodyparts=$bodyparts, equipments=$equipments)"
    }

    companion object {

        fun from(name: String,
                 owner: Account,
                 spells: Set<Spell>,
                 bodyparts: Set<Bodypart>,
                 id: String? = null): Hero {
            return Hero(id, name, owner, spells, bodyparts)
        }
    }
}