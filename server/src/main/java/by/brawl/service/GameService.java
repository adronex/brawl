package by.brawl.service;

import by.brawl.entity.Account;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.util.Pair;

import java.util.Map;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 26.03.2017.
 */
public interface GameService {
    Boolean isAccountInAnyRoom(Account account);

    Map<String, Pair<String, String>> getActiveRooms();
}
