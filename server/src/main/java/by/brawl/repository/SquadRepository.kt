package by.brawl.repository

import by.brawl.entity.Account
import by.brawl.entity.Squad
import org.springframework.data.repository.CrudRepository

interface SquadRepository : CrudRepository<Squad, String> {

    fun findByOwner(owner: Account): Set<Squad>
}
