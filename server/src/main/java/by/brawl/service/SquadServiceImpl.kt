package by.brawl.service

import by.brawl.entity.Account
import by.brawl.entity.Squad
import by.brawl.repository.SquadRepository
import by.brawl.util.Exceptions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
internal class SquadServiceImpl
constructor(private val repository: SquadRepository,
            private val securityService: SecurityService) : SquadService {

    override val mySquads = repository.findByOwner(securityService.currentAccount)

    override fun getWithAuthorityCheck(authority: Account, squadId: String): Squad {
        val squad = repository.findOne(squadId) ?: throw Exceptions.produceAccessDenied(LOG, "Squad with id $squadId is not existing in database. Calling account: $authority")
        if (authority != squad.owner) {
            throw Exceptions.produceAccessDenied(LOG, "Squad with id $squadId belongs to account ${squad.owner.username} but was queried by account $authority")
        }
        return squad
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(SquadServiceImpl::class.java)
    }

}