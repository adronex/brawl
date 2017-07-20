package by.brawl.service

import by.brawl.entity.Hero
import by.brawl.repository.HeroRepository
import by.brawl.util.Exceptions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class HeroServiceImpl
constructor(private val repository: HeroRepository) : HeroService {

    override fun findOne(id: String): Hero = repository.findOne(id) ?:
            throw Exceptions.produceIllegalArgument(LOG, "Hero with id $id does not exists!")

    companion object {

        private val LOG = LoggerFactory.getLogger(HeroServiceImpl::class.java)
    }

}