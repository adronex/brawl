package by.brawl.service

import by.brawl.dto.HeroDto
import by.brawl.dto.SubmitHeroDto
import by.brawl.entity.Hero

interface HeroService {

    fun findMy(): List<HeroDto>

    fun getById(id: String): Hero

    fun submit(submitHeroDto: SubmitHeroDto)
}