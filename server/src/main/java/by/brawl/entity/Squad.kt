package by.brawl.entity

import java.util.*
import javax.persistence.*

@Entity
class Squad : NamedEntity() {

    @ManyToOne
    lateinit var owner: Account

    @ManyToMany
    @JoinTable(name = "hero_squad",
            joinColumns = arrayOf(JoinColumn(name = "squad_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "hero_id")))
    val heroes: Set<Hero> = LinkedHashSet()

}