package by.brawl.dto

import by.brawl.entity.Hero
import by.brawl.entity.IdEntity
import by.brawl.entity.Squad
import by.brawl.entity.bodypart.Bodypart

class PreGameSquadDto(squad: Squad) {
    val id: String = squad.id!!
    val name: String = squad.name
    val heroes = squad.heroes.map { HeroDto(it) }
}

class HeroDto(hero: Hero) {
    val id = hero.id
    val name = hero.name
    val spells = hero.spells.map { IdDto(it) }
    val bodyparts = hero.bodyparts.map { BodypartDto(it) }
}

class SubmitHeroDto(val id:String?,
                    val name:String,
                    val spells:Set<String>,
                    // todo: remove mock
                    val bodyparts:Set<String> = setOf()) {
}

class BodypartDto(bodypart: Bodypart) {
    val id = bodypart.id
    val type = bodypart.type
}

class IdDto(idEntity: IdEntity) {
    val id = idEntity.id
}