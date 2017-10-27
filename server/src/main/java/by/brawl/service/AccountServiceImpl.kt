package by.brawl.service

import by.brawl.repository.AccountRepository
import by.brawl.util.Exceptions
import by.brawl.ws.holder.gamesession.GameSessionsPool
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Default class description.

 * @author P.Sinitsky.
 * *         Created on 25.03.2017.
 */
@Service
internal class AccountServiceImpl
constructor(private val repository: AccountRepository) : AccountService {

    override fun findByEmail(email: String) = repository.findByEmail(email) ?:
            throw IllegalArgumentException("Account with email $email does not exists!")

}