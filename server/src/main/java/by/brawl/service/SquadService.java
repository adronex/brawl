package by.brawl.service;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;

public interface SquadService {

    Squad getWithAuthorityCheck(Account authority, String squadId);
}
