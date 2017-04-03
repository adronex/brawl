package by.brawl.repository;

import by.brawl.entity.Hero;
import org.springframework.data.repository.CrudRepository;

public interface HeroRepository extends CrudRepository<Hero, String> {
}
