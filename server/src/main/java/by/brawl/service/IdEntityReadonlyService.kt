package by.brawl.service

import by.brawl.entity.Hero
import by.brawl.entity.IdEntity
import by.brawl.entity.Spell
import by.brawl.repository.HeroReadonlyRepository
import by.brawl.repository.IdEntityReadonlyRepository
import by.brawl.repository.SpellReadonlyRepository
import org.springframework.stereotype.Service

open class IdEntityReadonlyService<E>(protected val repository: IdEntityReadonlyRepository<E>) where E: IdEntity {
    fun findOne(id: String): E = repository.findOne(id)
    fun findAll(ids: Iterable<String>): List<E> = repository.findAll(ids).toList()
}

@Service
class SpellReadonlyService(repository: SpellReadonlyRepository): IdEntityReadonlyService<Spell>(repository)

@Service
class HeroReadonlyService(repository: HeroReadonlyRepository): IdEntityReadonlyService<Hero>(repository)