package by.brawl.ws.service;

import by.brawl.dto.ExposableSpellDto;
import by.brawl.dto.MulliganDto;
import by.brawl.dto.NewHeroDto;
import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import by.brawl.service.AccountService;
import by.brawl.ws.dto.HeroDto;
import by.brawl.ws.dto.JsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private AccountService accountService;

    private Set<String> exposedSpellIds = new HashSet<>();

    @Override
    public void createTwoPlayersGame(Pair<WebSocketSession, Squad> firstPlayer,
                                     Pair<WebSocketSession, Squad> secondPlayer) {

        sendMulliganData(firstPlayer, secondPlayer);

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

    private void sendDto(WebSocketSession session, JsonDto dto) {
        try {
            session.sendMessage(new TextMessage(dto.asJson()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MulliganDto hideNonExposedSpells(MulliganDto dto) {

        dto.getEnemyHeroes().forEach(h ->
                h.getSpells().removeIf(spellDto -> !exposedSpellIds.contains(spellDto.getId()))
        );
        return dto;
    }
}