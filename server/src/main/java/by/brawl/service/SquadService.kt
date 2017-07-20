package by.brawl.service

import by.brawl.entity.Account
import by.brawl.entity.Squad

interface SquadService {

    val mySquads: Set<Squad>

    fun getWithAuthorityCheck(authority: Account, squadId: String): Squad

}