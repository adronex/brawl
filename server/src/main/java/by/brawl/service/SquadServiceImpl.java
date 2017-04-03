package by.brawl.service;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import by.brawl.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class SquadServiceImpl implements SquadService {

    @Autowired
    private SquadRepository repository;
    @Autowired
    private SecurityService securityService;

    @Override
    public Set<Squad> getMySquads() {
        Account account = securityService.getCurrentAccount();
        return repository.findByOwner(account);
    }

    @Override
    public Squad getWithAuthorityCheck(Account authority, String squadId) {
        if (authority == null) {
            throw new NullPointerException();
        }
        Squad squad = repository.findOne(squadId);
        if (squad == null || !authority.equals(squad.getOwner())) {
            throw new AccessDeniedException("huinana");
        }
        return squad;
    }
}
