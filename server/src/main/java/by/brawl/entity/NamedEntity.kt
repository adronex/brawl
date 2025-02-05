package by.brawl.entity

import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class NamedEntity : IdEntity() {

    lateinit var name: String
        protected set

    override fun toString(): String {
        return "NamedEntity{" +
                "name='" + name + '\'' +
                '}'
    }

}