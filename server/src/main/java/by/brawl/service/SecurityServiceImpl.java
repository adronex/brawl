package by.brawl.service;

import by.brawl.entity.Account;
import org.springframework.stereotype.Service;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 26.03.2017.
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public Account getCurrentAccount() {
        return new Account();
    }
}
