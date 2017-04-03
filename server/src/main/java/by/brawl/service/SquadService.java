package by.brawl.service;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;

import java.util.Set;

public interface SquadService {

    Set<Squad> getMySquads();

    Squad getWithAuthorityCheck(Account authority, String squadId);
}
