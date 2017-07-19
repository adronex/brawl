package by.brawl.service

import by.brawl.entity.Account
import by.brawl.entity.Squad
import by.brawl.repository.SquadRepository
import by.brawl.util.Exceptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.Objects

@Service
internal class SquadServiceImpl
constructor(private val repository: SquadRepository,
            private val securityService: SecurityService) : SquadService {

    override val mySquads: Set<Squad>
        get() {
            val account = securityService.currentAccount
            return repository.findByOwner(account)
        }

    override fun getWithAuthorityCheck(authority: Account, squadId: String): Squad {
        val squad = repository.findOne(squadId) ?: throw Exceptions.produceAccessDenied(LOG, "Squad with id {0} is not existing in database. Calling account: {1}",
                squadId, authority)
        if (authority != squad.owner) {
            throw Exceptions.produceAccessDenied(LOG, "Squad with id {0} is belongs for account {1} but was queried by account {2}",
                    squadId, squad.owner.username, authority)
        }
        return squad
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(SquadServiceImpl::class.java)
    }
}