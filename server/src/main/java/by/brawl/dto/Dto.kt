package by.brawl.dto

import by.brawl.entity.Hero
import by.brawl.entity.IdEntity
import by.brawl.entity.Squad

class PreGameSquadDto(squad: Squad) {
    val id: String = squad.id
    val name: String = squad.name
    val heroes = squad.heroes.map { HeroDto(it) }
}

class HeroDto(hero: Hero) {
    val id = hero.id
    val name = hero.name
    val spells = hero.spells.map { IdDto(it) }
    val bodyparts = hero.bodyparts.map { IdDto(it) }
}

class IdDto(idEntity: IdEntity) {
    val id = idEntity.id
}