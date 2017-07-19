package by.brawl.service

import by.brawl.entity.Account
import by.brawl.repository.AccountRepository
import by.brawl.util.Exceptions
import by.brawl.ws.holder.gamesession.GameSessionsPool
import by.brawl.ws.service.MatchmakingServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Default class description.

 * @author P.Sinitsky.
 * *         Created on 25.03.2017.
 */
@Service
internal class AccountServiceImpl
constructor(private val repository: AccountRepository) : AccountService {

    override fun findByEmail(email: String) =
            repository.findByEmail(email) ?:
                    throw Exceptions.produceIllegalArgument(LOG, "Account with email {0} does not exists!", email)

    companion object {

        private val LOG = LoggerFactory.getLogger(GameSessionsPool::class.java)
    }

}
