package by.brawl.ws.holder

import by.brawl.entity.Squad

class SquadHolder {

    constructor(squad: Squad) {
        ownerName = squad.owner.username
        heroes = squad.heroes.map { HeroHolder(it, this) }
    }

    constructor(ownerName: String,
                heroes: List<HeroHolder>) {
        this.ownerName = ownerName
        this.heroes = heroes.map { HeroHolder(it, this) }
    }

    val ownerName: String
    val heroes: List<HeroHolder>
}