package by.brawl.repository

import by.brawl.entity.Account
import org.springframework.data.repository.CrudRepository

/**
 * Default class description.

 * @author P.Sinitsky.
 * *         Created on 25.03.2017.
 */
interface AccountRepository : CrudRepository<Account, String> {
    fun findByEmail(email: String): Account?
}
