package by.brawl.ws.service;

import by.brawl.entity.Squad;
import by.brawl.ws.dto.JsonDto;
import by.brawl.ws.newdto.BattlefieldDto;
import by.brawl.ws.newdto.MulliganDto;
import by.brawl.ws.pojo.BattlefieldHolder;
import by.brawl.ws.pojo.GameState;
import by.brawl.ws.pojo.HeroHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private BattlefieldHolder battlefieldHolder = new BattlefieldHolder();
    private GameState gameState = GameState.NOT_STARTED;

    private static final Integer BATTLEFIELD_HEROES_COUNT = 2;

    @Override
    public void createTwoPlayersGame(WebSocketSession firstSession, Squad firstSquad,
                                     WebSocketSession secondSession, Squad secondSquad) {

        if (!GameState.NOT_STARTED.equals(gameState)) {
            System.out.println("GAME STATE IS ILLEGAL");
            throw new IllegalStateException();
        }
        battlefieldHolder.addSquad(firstSession, firstSquad);
        battlefieldHolder.addSquad(secondSession, secondSquad);
        gameState = GameState.MULLIGAN;
        sendMulliganData();
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

        List<HeroHolder> mulliganHeroes = battlefieldHolder.getMulliganHeroes().get(playerKey);
        List<HeroHolder> battleHeroes = new ArrayList<>();

        for (String heroId : heroesIds) {
            HeroHolder battleHero = mulliganHeroes.stream()
                    .filter(h -> h.getId().equals(heroId))
                    .findFirst()
                    .orElseThrow(() -> new AccessDeniedException("Not your hero!"));

            battleHeroes.add(battleHero);
        }

        battlefieldHolder.getBattleHeroes().put(playerKey, battleHeroes);

        if (battlefieldHolder.getBattleHeroes().size() == 2) {
            battlefieldHolder.prepareGame();
            gameState = GameState.PLAYING;
            sendBattlefieldData();
        }
    }

    private void sendMulliganData() {
        battlefieldHolder.getSessions().forEach((key, value) -> {
            MulliganDto dto = new MulliganDto(
                    battlefieldHolder.getMulliganHeroes(),
                    battlefieldHolder.getHeroSpells(),
                    key
            );
            sendDto(value, dto);
        });
    }

    private void sendBattlefieldData() {
        battlefieldHolder.getSessions().forEach((key, value) -> {
            BattlefieldDto dto = new BattlefieldDto(battlefieldHolder,key);
            sendDto(value, dto);
        });
    }

    private void sendGameTurnData(WebSocketSession session) {


        sendDto(session, battlefieldHolder);
    }

    private void sendDto(WebSocketSession session, JsonDto dto) {
        try {
            session.sendMessage(new TextMessage(dto.asJson()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}