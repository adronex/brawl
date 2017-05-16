package by.brawl.service;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import by.brawl.repository.SquadRepository;
import by.brawl.util.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
class SquadServiceImpl implements SquadService {

    private static final Logger LOG = LoggerFactory.getLogger(SquadServiceImpl.class);

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
            throw Exceptions.produceNullPointer(LOG, "Trying to get squad when account is null");
        }
        Squad squad = repository.findOne(squadId);
        if (squad == null) {
            throw Exceptions.produceAccessDenied(LOG, "Squad with id {0} is not existing in database. Calling account: {1}",
                    squadId, authority);
        }
        if (!Objects.equals(authority, squad.getOwner())) {
            throw Exceptions.produceAccessDenied(LOG, "Squad with id {0} is belongs for account {1} but was queried by account {2}",
                    squadId, squad.getOwner().getUsername(), authority);
        }
        return squad;
    }
}