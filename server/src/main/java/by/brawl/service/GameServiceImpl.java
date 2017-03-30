package by.brawl.service;

import by.brawl.entity.Account;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 26.03.2017.
 */
@Service
class GameServiceImpl implements GameService {

    private Map<String, Pair<String, String>> activeRooms = new ConcurrentHashMap<>();

    @Override
    public Boolean isAccountInAnyRoom(Account account) {
        return activeRooms.entrySet().stream()
                // check all existing rooms for accountId
                .filter(entry -> {
                    String firstPlayerId = entry.getValue().getFirst();
                    String secondPlayerId = entry.getValue().getSecond();

                    return account.getId().equals(firstPlayerId)
                            || account.getId().equals(secondPlayerId);
                })
                // if there is room return its url
                .count() > 0;
    }

    @Override
    public Map<String, Pair<String, String>> getActiveRooms() {
        return activeRooms;
    }
}
