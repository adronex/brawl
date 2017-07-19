package by.brawl.dto

import by.brawl.entity.Squad

class PreGameSquadDto(squad: Squad) {
    val id: String? = squad.id
    val name: String? = squad.name
}
