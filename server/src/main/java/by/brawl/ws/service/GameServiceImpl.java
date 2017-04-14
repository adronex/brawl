package by.brawl.ws.service;

import by.brawl.entity.Spell;
import by.brawl.entity.Squad;
import by.brawl.ws.dto.JsonDto;
import by.brawl.ws.dto.MessageDto;
import by.brawl.ws.newdto.BattlefieldDto;
import by.brawl.ws.newdto.MulliganDto;
import by.brawl.ws.pojo.BattlefieldHolder;
import by.brawl.ws.pojo.GameState;
import by.brawl.ws.pojo.HeroHolder;
import by.brawl.ws.spell.SpellLogic;
import by.brawl.ws.spell.SuckerPunch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private BattlefieldHolder battlefieldHolder = new BattlefieldHolder();
    private GameState gameState = GameState.NOT_STARTED;

    private Map<String, SpellLogic> spellsPool = new HashMap<>();

    private static final Integer BATTLEFIELD_HEROES_COUNT = 2;

    @PostConstruct
    public void init() {
        SpellLogic suckerPunch = new SuckerPunch();
        spellsPool.put(suckerPunch.getId(), suckerPunch);
    }

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
        } else {
            sendDto(session, new MessageDto("Opponent is still choosing."));
        }
    }

    @Override
    public void castSpell(WebSocketSession session, String spellId, Integer target, Boolean enemy) {
        if (!GameState.PLAYING.equals(gameState)) {
            throw new IllegalStateException("Invalid game state!");
        }
        String playerKey = session.getPrincipal().getName();
        String currentHeroId = battlefieldHolder.getQueue().element();
        // check for your hero or not
        HeroHolder currentHero = battlefieldHolder.getBattleHeroes().get(playerKey)
                .stream()
                .filter(h -> h.getId().equals(currentHeroId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not your turn!"));
        // check for spell validity (if hero has it)
        Spell castedSpellId = battlefieldHolder.getHeroSpells().get(currentHeroId)
                .stream()
                .filter(s -> s.getId().equals(spellId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not your spell!"));
        SpellLogic castedSpell = spellsPool.get(castedSpellId.getId());
        // check target for validity
        Boolean cannotBeTargeted = (target == null && !castedSpell.getTargetable());
        Boolean validMyTarget = target != null && enemy != null && !enemy && castedSpell.getMyTargets().contains(target);
        Boolean validEnemyTarget = target != null && enemy != null && enemy && castedSpell.getEnemyTargets().contains(target);
        if (!cannotBeTargeted && !validMyTarget && !validEnemyTarget) {
            throw new IllegalArgumentException("Invalid target!");
        }
        // todo: cast spells right here
        battlefieldHolder.moveQueue();
        sendBattlefieldData();
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

    private void sendDto(WebSocketSession session, JsonDto dto) {
        try {
            session.sendMessage(new TextMessage(dto.asJson()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}