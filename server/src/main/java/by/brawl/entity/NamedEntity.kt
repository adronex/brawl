package by.brawl.entity

import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class NamedEntity : IdEntity() {

    var name: String? = null
        protected set

    override fun toString(): String {
        return "NamedEntity{" +
                "name='" + name + '\'' +
                '}'
    }
}
