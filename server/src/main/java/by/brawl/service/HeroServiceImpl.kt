package by.brawl.service

import by.brawl.entity.Hero
import by.brawl.repository.HeroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HeroServiceImpl
constructor(private val repository: HeroRepository) : HeroService {

    override fun findOne(id: String): Hero? = repository.findOne(id)
}
