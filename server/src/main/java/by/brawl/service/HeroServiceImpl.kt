package by.brawl.service

import by.brawl.dto.HeroDto
import by.brawl.dto.SubmitHeroDto
import by.brawl.entity.Hero
import by.brawl.entity.bodypart.Bodypart
import by.brawl.repository.HeroRepository
import by.brawl.util.Exceptions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class HeroServiceImpl(private val repository: HeroRepository,
                      private val securityService: SecurityService,
                      private val spellReadonlyService: SpellReadonlyService) : HeroService {

    override fun findMy() = repository.findByOwner(securityService.getCurrentAccount()).map { HeroDto(it) }

    override fun getById(id: String): Hero = repository.findOne(id) ?:
            throw Exceptions.produceIllegalArgument(LOG, "Hero with id $id does not exists!")

    override fun submit(submitHeroDto: SubmitHeroDto) {
        val account = securityService.getCurrentAccount()
        val hero = if (submitHeroDto.id == null) {
            Hero.from(submitHeroDto.name, account, spellReadonlyService.findAll(submitHeroDto.spells).toSet(), getDefaultBodyparts())
        } else {
            val existingHero = getById(submitHeroDto.id)
            if (existingHero.owner != account) {
                throw Exceptions.produceIllegalArgument(LOG, "Hero owner is ${existingHero.owner.username} where requester is ${account.username}")
            }
            Hero.from(submitHeroDto.name, account, submitHeroDto.spells.map { spellReadonlyService.findOne(it) }.toSet(), getDefaultBodyparts(), existingHero.id)
        }
        repository.save(hero)
    }

    private fun getDefaultBodyparts(): Set<Bodypart> {
        return setOf()
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(HeroServiceImpl::class.java)
    }

}