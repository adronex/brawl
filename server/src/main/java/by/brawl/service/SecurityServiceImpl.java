package by.brawl.service;

import by.brawl.entity.Account;
import by.brawl.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 26.03.2017.
 */
@Service
class SecurityServiceImpl implements SecurityService, UserDetailsService {

    private AccountRepository accountRepository;

    @Autowired
    public SecurityServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("Huinana");
        }
        return account;
    }

    @Override
    public Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UsernameNotFoundException("Huinana");
        }
        Account currentAccount = accountRepository.findByEmail(authentication.getName());
        if (currentAccount == null) {
            throw new UsernameNotFoundException("Huinana");
        }
        return currentAccount;
    }
}
