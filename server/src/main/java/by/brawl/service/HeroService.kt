package by.brawl.service

import by.brawl.entity.Hero

interface HeroService {

    fun findOne(id: String): Hero?
}
