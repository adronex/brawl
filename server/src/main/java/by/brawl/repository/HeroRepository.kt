package by.brawl.repository

import by.brawl.entity.Account
import by.brawl.entity.Hero
import org.springframework.data.repository.CrudRepository

interface HeroRepository : CrudRepository<Hero, String> {

    fun findByOwner(owner: Account): Set<Hero>
}