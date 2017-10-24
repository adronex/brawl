package by.brawl.repository

import by.brawl.entity.Hero
import by.brawl.entity.IdEntity
import by.brawl.entity.Spell
import org.springframework.data.repository.CrudRepository

interface IdEntityReadonlyRepository<E>: CrudRepository<E, String> where E:IdEntity  {
}

interface SpellReadonlyRepository: IdEntityReadonlyRepository<Spell> {
}

interface HeroReadonlyRepository: IdEntityReadonlyRepository<Hero> {
}