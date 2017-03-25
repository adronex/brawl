package by.brawl.service;

import by.brawl.entity.Account;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 25.03.2017.
 */
public interface AccountService {
    Account findByEmail(String email);
}
