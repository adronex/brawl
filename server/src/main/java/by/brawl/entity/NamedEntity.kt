package by.brawl.entity

import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class NamedEntity(id: String? = null,
                           val name: String) : IdEntity(id) {

    override fun toString(): String {
        return "NamedEntity{" +
                "name='" + name + '\'' +
                '}'
    }

}