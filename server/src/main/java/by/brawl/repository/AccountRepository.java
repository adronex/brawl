package by.brawl.repository;

import by.brawl.entity.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 25.03.2017.
 */
public interface AccountRepository extends CrudRepository<Account, String> {
    Account findByEmail(String email);
}
