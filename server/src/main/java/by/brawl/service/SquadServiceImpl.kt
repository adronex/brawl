package by.brawl.service

import by.brawl.entity.Account
import by.brawl.entity.Squad
import by.brawl.repository.SquadRepository
import org.springframework.stereotype.Service

@Service
internal class SquadServiceImpl
constructor(private val repository: SquadRepository,
            private val securityService: SecurityService) : SquadService {

    override fun getMySquads() = repository.findByOwner(securityService.getCurrentAccount())

    override fun getWithAuthorityCheck(authority: Account, squadId: String): Squad {
        val squad = repository.findOne(squadId) ?: throw IllegalAccessException("Squad with id $squadId is not existing in database. Calling account: $authority")
        if (authority != squad.owner) {
            throw IllegalAccessException("Squad with id $squadId belongs to account ${squad.owner.username} but was queried by account $authority")
        }
        return squad
    }

}