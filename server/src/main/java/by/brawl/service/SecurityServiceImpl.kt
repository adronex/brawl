package by.brawl.service

import by.brawl.entity.Account
import by.brawl.repository.AccountRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
internal class SecurityServiceImpl
constructor(private val accountRepository: AccountRepository) : SecurityService, UserDetailsService {

    override fun loadUserByUsername(username: String) =
            accountRepository.findByEmail(username) ?: throw UsernameNotFoundException("User with name $username is not registered in database")

    override fun getCurrentAccount(): Account {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null || authentication.name == null) {
                throw UsernameNotFoundException("Authentication object is empty")
            }
            return accountRepository.findByEmail(authentication.name) ?: throw UsernameNotFoundException("Authentication object isn't connected to any existing account")
        }

}