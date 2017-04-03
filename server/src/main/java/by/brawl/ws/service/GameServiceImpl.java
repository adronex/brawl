package by.brawl.ws.service;

import by.brawl.dto.MulliganDto;
import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.entity.IdEntity;
import by.brawl.entity.Squad;
import by.brawl.service.AccountService;
import by.brawl.service.HeroService;
import by.brawl.ws.dto.JsonDto;
import by.brawl.ws.pojo.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.jetty.JettyWebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private HeroService heroService;

    private Set<String> exposedSpellIds = new HashSet<>();
    private Map<String, List<Hero>> battlefield = new HashMap<>();
    private GameState gameState = GameState.NOT_STARTED;

    private static final Integer BATTLEFIELD_HEROES_COUNT = 2;

    @Override
    public void createTwoPlayersGame(Pair<WebSocketSession, Squad> firstPlayer,
                                     Pair<WebSocketSession, Squad> secondPlayer) {

        if (!GameState.NOT_STARTED.equals(gameState)) {
            throw new IllegalStateException();
        }
        sendMulliganData(firstPlayer, secondPlayer);
        gameState = GameState.MULLIGAN;
    }

    @Override
    public void setHeroesPositions(WebSocketSession session, List<String> heroesIds) {
        if (!GameState.MULLIGAN.equals(gameState)) {
            throw new IllegalStateException();
        }
        if (heroesIds.size() != BATTLEFIELD_HEROES_COUNT) {
            throw new IllegalArgumentException();
        }
        String playerKey = session.getPrincipal().getName();
        List<Hero> heroesAtBattlefield = heroesIds
                .stream()
                .map(id -> heroService.findOne(id))
                .collect(Collectors.toList());

        for (Hero hero : heroesAtBattlefield) {
            // TODO: remake for account entity
            if (!playerKey.equals(hero.getOwner().getUsername())) {
                throw new AccessDeniedException("huinana");
            }
        }

        battlefield.put(playerKey, heroesAtBattlefield);
        if (battlefield.size() == 2) {

        }
    }

    private void sendMulliganData(Pair<WebSocketSession, Squad> firstPlayer,
                                  Pair<WebSocketSession, Squad> secondPlayer) {
        Account first = accountService.findByEmail(firstPlayer.getFirst().getPrincipal().getName());
        Account second = accountService.findByEmail(secondPlayer.getFirst().getPrincipal().getName());

        MulliganDto firstPlayerMessage = new MulliganDto(firstPlayer.getSecond(), secondPlayer.getSecond(), first);
        firstPlayerMessage = hideNonExposedSpells(firstPlayerMessage);

        MulliganDto secondPlayerMessage = new MulliganDto(firstPlayer.getSecond(), secondPlayer.getSecond(), second);
        secondPlayerMessage = hideNonExposedSpells(secondPlayerMessage);

        sendDto(firstPlayer.getFirst(), firstPlayerMessage);
        sendDto(secondPlayer.getFirst(), secondPlayerMessage);
    }

    private void sendStartGameDta() {
        // todo: keep player sessions
        WebSocketSession session = new JettyWebSocketSession(null);
        // todo: replace ids with correct values
        List<String> firstPlayerPositions = battlefield.get("1").stream().map(IdEntity::getId).collect(Collectors.toList());
        List<String> secondPlayerPositions = battlefield.get("2").stream().map(IdEntity::getId).collect(Collectors.toList());
        // todo: create battlefield DTO
        sendDto(session, null);
    }

    private MulliganDto hideNonExposedSpells(MulliganDto dto) {

        dto.getEnemyHeroes().forEach(h ->
                h.getSpells().removeIf(spellDto -> !exposedSpellIds.contains(spellDto.getId()))
        );
        return dto;
    }

    private void sendDto(WebSocketSession session, JsonDto dto) {
        try {
            session.sendMessage(new TextMessage(dto.asJson()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}