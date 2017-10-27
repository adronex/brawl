package by.brawl.service

import by.brawl.dto.HeroDto
import by.brawl.dto.IdDto
import by.brawl.dto.SubmitHeroDto
import by.brawl.entity.Hero
import by.brawl.entity.bodypart.Bodypart
import by.brawl.repository.HeroRepository
import by.brawl.util.Exceptions
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class HeroServiceImpl(private val repository: HeroRepository,
                      private val securityService: SecurityService,
                      private val spellReadonlyService: SpellReadonlyService) : HeroService {

    override fun findMy() = repository.findByOwner(securityService.getCurrentAccount()).map { HeroDto(it) }

    override fun getById(id: String): Hero = repository.findOne(id) ?:
            throw IllegalArgumentException("Hero with id $id does not exists!")

    override fun submit(submitHeroDto: SubmitHeroDto): HeroDto {
        val account = securityService.getCurrentAccount()
        val hero = if (submitHeroDto.id == null) {
            Hero.from(submitHeroDto.name, account, spellReadonlyService.findAll(submitHeroDto.spells).toSet(), getDefaultBodyparts())
        } else {
            val existingHero = getById(submitHeroDto.id)
            if (existingHero.owner != account) {
                throw IllegalArgumentException("Hero owner is ${existingHero.owner.username} where requester is ${account.username}")
            }
            Hero.from(submitHeroDto.name, account, submitHeroDto.spells.map { spellReadonlyService.findOne(it) }.toSet(), getDefaultBodyparts(), existingHero.id)
        }
        repository.save(hero)
        return HeroDto(hero)
    }

    private fun getDefaultBodyparts(): Set<Bodypart> {
        return setOf()
    }

    override fun delete(id: String): IdDto {
        val hero = repository.findOne(id)
        val account = securityService.getCurrentAccount()
        if (hero.owner != account) {
            throw IllegalAccessException("Account ${account.username} tries to delete ${hero.owner.username}'s hero")
        }
        repository.delete(hero)
        return IdDto(hero)
    }

}