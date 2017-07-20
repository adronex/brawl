package by.brawl.service

import by.brawl.entity.Account

interface SecurityService {

    fun getCurrentAccount(): Account

}