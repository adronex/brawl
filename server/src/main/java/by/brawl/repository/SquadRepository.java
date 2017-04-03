package by.brawl.repository;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SquadRepository extends CrudRepository<Squad, String> {

    Set<Squad> findByOwner(Account owner);
}
