package by.brawl.repository

import by.brawl.entity.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, String> {

    fun findByEmail(email: String): Account?

}