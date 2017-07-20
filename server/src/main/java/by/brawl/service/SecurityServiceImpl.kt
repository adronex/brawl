package by.brawl.service

import by.brawl.entity.Account
import by.brawl.repository.AccountRepository
import by.brawl.util.Exceptions
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
internal class SecurityServiceImpl
constructor(private val accountRepository: AccountRepository) : SecurityService, UserDetailsService {

    override fun loadUserByUsername(username: String) =
            accountRepository.findByEmail(username) ?: throw Exceptions.produceUsernameNotFound(LOG, "User with name $username is not registered in database")

    override fun getCurrentAccount(): Account {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null || authentication.name == null) {
                throw Exceptions.produceUsernameNotFound(LOG, "Authentication object is empty")
            }
            return accountRepository.findByEmail(authentication.name) ?: throw Exceptions.produceUsernameNotFound(LOG, "Authentication object doesn't connect to any existing account")
        }

    companion object {

        private val LOG = LoggerFactory.getLogger(SecurityServiceImpl::class.java)
    }

}