package by.brawl.service;

import by.brawl.entity.Account;
import by.brawl.repository.AccountRepository;
import by.brawl.util.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
class SecurityServiceImpl implements SecurityService, UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private AccountRepository accountRepository;

    @Autowired
    public SecurityServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            throw Exceptions.produceUsernameNotFound(LOG, "User with name {0} is not registered in database", username);
        }
        return account;
    }

    @Override
    public Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw Exceptions.produceUsernameNotFound(LOG, "Authentication object is empty");
        }
        Account currentAccount = accountRepository.findByEmail(authentication.getName());
        if (currentAccount == null) {
            throw Exceptions.produceUsernameNotFound(LOG, "Authentication object doesn't connect to any existing account");
        }
        return currentAccount;
    }
}
