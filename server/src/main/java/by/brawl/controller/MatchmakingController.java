package by.brawl.controller;

import by.brawl.service.GameService;
import by.brawl.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 26.03.2017.
 */
@RestController
@RequestMapping("/api/mm")
public class MatchmakingController {

    private GameService gameService;
    private SecurityService securityService;

    @Autowired
    public MatchmakingController(GameService gameService,
                                 SecurityService securityService) {
        this.gameService = gameService;
        this.securityService = securityService;
    }

    @GetMapping(path = "/new")
    public String findOpponent(@RequestParam String squadId) {
        // check squad belongs to current account
        if (!gameService.isAccountInAnyRoom(securityService.getCurrentAccount())) {
            return "new url";
        }
        throw new AccessDeniedException("Already in room!");
        // check squad not fighting right now
        // get squad from database
        // put squad into mm queue
        // on queue resolve create room
        // create websocket and return connection parameters

    }

    @GetMapping(path = "/ifExists")
    public String checkForReconnect(@RequestParam String accountId) {
        return gameService.getActiveRooms().entrySet().stream()
                // check all existing rooms for accountId
                .filter(entry -> {
                    String firstPlayerId = entry.getValue().getFirst();
                    String secondPlayerId = entry.getValue().getSecond();

                    return firstPlayerId.equals(accountId)
                            || secondPlayerId.equals(accountId);
                })
                // if there is room return its url
                .findFirst()
                .map(Map.Entry::getKey)
                // else return NULL
                .orElse(null);
    }
}
