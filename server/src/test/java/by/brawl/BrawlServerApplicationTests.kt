package by.brawl

import by.brawl.entity.Account
import by.brawl.entity.Hero
import by.brawl.service.AccountService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component
import org.springframework.test.context.junit4.SpringRunner

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(SpringRunner::class)
@SpringBootTest
@Component
open class BrawlServerApplicationTests {

    @Autowired
    private val accountService: AccountService? = null

    @Test
    fun testAccountRepository() {
        val account = accountService!!.findByEmail("adronex303@gmail.com")
        assertNotNull(account)
    }

}
