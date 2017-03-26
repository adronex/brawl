package by.brawl;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class BrawlServerApplicationTests {

	@Autowired
    private AccountService accountService;

	@Test
	public void testAccountRepository() {
        Account account = accountService.findByEmail("adronex303@gmail.com");
        assertNotNull(account);
        assertEquals(5, account.getAvailableSpells().size());
        assertEquals(1, account.getSquads().size());
        assertEquals(2, account.getHeroes().size());
        Set<Hero> heroesInSquad = account.getSquads().stream().findFirst().get().getHeroes();
        assertEquals(2, heroesInSquad.size());
        assertEquals(4, heroesInSquad.stream().findFirst().get().getSpells().size());
	}

}
