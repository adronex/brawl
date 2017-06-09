package by.brawl.ws.service;

import by.brawl.entity.Squad;
import by.brawl.util.Exceptions;
import by.brawl.util.Mappers;
import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.GameState;
import by.brawl.ws.holder.HeroHolder;
import by.brawl.ws.holder.SpellHolder;
import by.brawl.ws.holder.gamesession.GameSession;
import by.brawl.ws.holder.gamesession.GameSessionsPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
class GameServiceImpl implements GameService {

    private static final Integer BATTLEFIELD_HEROES_COUNT = 4;
    private static final Logger LOG = LoggerFactory.getLogger(GameServiceImpl.class);
    @Autowired
    private GameSessionsPool gameSessionsPool;
    @Autowired
    private SpellCastService spellCastService;

    @Override
    public void createTwoPlayersGame(GameSession firstSession,
                                     GameSession secondSession,
                                     Squad firstSquad,
                                     Squad secondSquad) {

        BattlefieldHolder battlefieldHolder = new BattlefieldHolder();
        battlefieldHolder.addSquad(firstSquad);
        battlefieldHolder.addSquad(secondSquad);
        firstSession.setBattlefieldHolder(battlefieldHolder);
        secondSession.setBattlefieldHolder(battlefieldHolder);
        battlefieldHolder.setGameState(GameState.MULLIGAN);
        gameSessionsPool.sendMulliganData(battlefieldHolder);
    }

    @Override
    public void setHeroesPositions(GameSession session, List<String> heroesIds) {

        BattlefieldHolder battlefieldHolder = getBattlefieldHolderAndCheckState(session, GameState.MULLIGAN);
        if (heroesIds.size() != BATTLEFIELD_HEROES_COUNT) {
            throw Exceptions.produceIllegalArgument(
                    LOG,
                    "Wrong heroes in battle count. Expected: {0}, actual: {1}",
                    BATTLEFIELD_HEROES_COUNT,
                    heroesIds.size()
            );
        }

        List<HeroHolder> mulliganHeroes = battlefieldHolder.getMulliganHeroes().get(session.getId());
        List<HeroHolder> battleHeroes = new ArrayList<>();

        for (String heroId : heroesIds) {
            HeroHolder battleHero = mulliganHeroes
                    .stream()
                    .filter(h -> h.getId().equals(heroId))
                    .findFirst()
                    .orElseThrow(() -> Exceptions
                            .produceAccessDenied(
                                    LOG,
                                    "Player {0} trying to choose hero with id {1} from available ids: {2}",
                                    session.getId(),
                                    heroId,
                                    Arrays.toString(mulliganHeroes.stream()
                                                                  .map(HeroHolder::getId)
                                                                  .toArray()))
                    );

            battleHeroes.add(battleHero);
        }

        battlefieldHolder.addBattleHeroes(session.getId(), battleHeroes);

        if (battlefieldHolder.isReadyForBattle()) {
            battlefieldHolder.prepareGame();
            battlefieldHolder.setGameState(GameState.PLAYING);
            gameSessionsPool.sendBattlefieldData(battlefieldHolder);
        } else {
            session.sendInfoMessage("Opponent is still choosing.");
        }
    }

    @Override
    public void castSpell(GameSession session, String spellId, Integer victimPosition, Boolean forEnemy) {

        BattlefieldHolder battlefieldHolder = getBattlefieldHolderAndCheckState(session, GameState.PLAYING);
        String playerKey = session.getId();
        HeroHolder currentHero = battlefieldHolder.getQueue().element();

        if (!battlefieldHolder.getBattleHeroes(playerKey, false).contains(currentHero)) {
            throw Exceptions.produceAccessDenied(
                    LOG,
                    "Player {0} tries to cast spell in opponents turn",
                    playerKey
            );
        }

        // todo: check victimPosition and forEnemy for both being null or not null
        if (!isSpellValid(currentHero, spellId, playerKey)) {
            return;
        }

        battlefieldHolder = spellCastService.castSpell(spellId,
                                                       session.getId(),
                                                       currentHero.getId(),
                                                       victimPosition,
                                                       forEnemy,
                                                       battlefieldHolder);

        if (battlefieldHolder.isGameFinished()) {
            battlefieldHolder.setGameState(GameState.END);
        } else {
            battlefieldHolder.incrementStep();
        }

        gameSessionsPool.sendBattlefieldData(battlefieldHolder);
    }

    private Boolean isSpellValid(HeroHolder currentHero, String spellId, String playerKey) {
        Boolean castedSpellBelongsToCurrentHero = currentHero.getAllSpells().stream()
                                                             .filter(spellHolder -> Objects.equals(spellHolder.getId(), spellId))
                                                             .count() > 0;
        if (!castedSpellBelongsToCurrentHero) {
            Object availableSpellsIds[] = currentHero.getAllSpells().stream().map(SpellHolder::getId).toArray();
            throw Exceptions.produceIllegalArgument(
                    LOG,
                    "Player {0} casted spell {1} that does not belong to current hero. Available spells are {2}",
                    playerKey,
                    spellId,
                    Arrays.toString(availableSpellsIds)
            );
        }
        Boolean castedSpellAvailable = currentHero.getAvailableSpells().stream()
                                                  .filter(spellHolder -> Objects.equals(spellHolder.getId(), spellId))
                                                  .count() > 0;
        if (!castedSpellAvailable) {
            List<String> availableSpellsIds = Mappers.asList(currentHero.getAvailableSpells(), SpellHolder::getId);
            throw Exceptions.produceIllegalArgument(
                    LOG,
                    "Player {0} casted spell {1} that is not available (on cooldown/suspended/etc). Available spells are {2}",
                    playerKey,
                    spellId,
                    availableSpellsIds.toString()
            );
        }
        return true;
    }

    private BattlefieldHolder getBattlefieldHolderAndCheckState(GameSession session, GameState gameState) {

        if (session.getBattlefieldHolder() == null) {
            throw Exceptions.produceNullPointer(
                    LOG,
                    "User {0} tries to play game without preliminary matchmaking",
                    session.getId()
            );
        }

        if (!gameState.equals(session.getBattlefieldHolder().getGameState())) {
            throw Exceptions.produceIllegalState(
                    LOG,
                    "Illegal game state. Expected: {0}, actual: {1}. Initiator: {2}",
                    gameState,
                    session.getBattlefieldHolder().getGameState(),
                    session.getId()
            );
        }

        return session.getBattlefieldHolder();
    }
}