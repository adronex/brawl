package by.brawl.service

import by.brawl.entity.Account

interface AccountService {
    fun findByEmail(email: String): Account
}
