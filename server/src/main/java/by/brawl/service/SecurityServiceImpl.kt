package by.brawl.service

import by.brawl.entity.Account
import by.brawl.repository.AccountRepository
import by.brawl.util.Exceptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
internal class SecurityServiceImpl
constructor(private val accountRepository: AccountRepository) : SecurityService, UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val account = accountRepository.findByEmail(username) ?: throw Exceptions.produceUsernameNotFound(LOG, "User with name {0} is not registered in database", username)
        return account
    }

    override val currentAccount: Account
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null || authentication.name == null) {
                throw Exceptions.produceUsernameNotFound(LOG, "Authentication object is empty")
            }
            val currentAccount = accountRepository.findByEmail(authentication.name) ?: throw Exceptions.produceUsernameNotFound(LOG, "Authentication object doesn't connect to any existing account")
            return currentAccount
        }

    companion object {

        private val LOG = LoggerFactory.getLogger(SecurityServiceImpl::class.java)
    }
}
