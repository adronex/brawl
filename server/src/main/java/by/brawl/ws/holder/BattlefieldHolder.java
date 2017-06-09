package by.brawl.ws.holder;

import by.brawl.entity.Squad;
import by.brawl.util.Exceptions;
import by.brawl.util.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class BattlefieldHolder {

    private static final Logger LOG = LoggerFactory.getLogger(BattlefieldHolder.class);
    private static final Integer PLAYERS_COUNT = 2;

    private GameState gameState = GameState.NOT_STARTED;
    private Integer currentStep = 0;

    private Map<String, List<HeroHolder>> mulliganHeroes = new HashMap<>();
    private Map<String, List<HeroHolder>> battleHeroes = new HashMap<>();
    private Set<String> connectedAccountsIds = new HashSet<>();
    private Queue<HeroHolder> queue = new LinkedList<>();
    private List<StepLogHolder> battleLog = new ArrayList<>();

    public void addSquad(Squad squad) {
        List<HeroHolder> heroes = Mappers.asList(squad.getHeroes(), HeroHolder::new);
        mulliganHeroes.put(squad.getOwner().getUsername(), heroes);
        connectedAccountsIds.add(squad.getOwner().getUsername());
    }

    public void addBattleHeroes(String playerId, List<HeroHolder> battleHeroes) {
        this.battleHeroes.put(playerId, battleHeroes);
    }

    public Boolean isReadyForBattle() {
        if (battleHeroes.size() > PLAYERS_COUNT) {
            throw Exceptions.produceIllegalState(
                    LOG,
                    "Two-players battlefield contains more players than maximum allowed: {0}",
                    battleHeroes.size()
            );
        }
        return battleHeroes.size() == PLAYERS_COUNT;
    }

    public void prepareGame() {
        battleHeroes.values().forEach(heroHolders -> queue.addAll(heroHolders));
        mulliganHeroes.clear();
    }

    public void moveQueueWithDeadRemoval() {
        HeroHolder heroToMove = queue.remove();
        queue.add(heroToMove);

        List<HeroHolder> newQueue = queue.stream()
                                         .filter(HeroHolder::isAlive)
                                         .collect(Collectors.toList());
        queue.clear();
        queue.addAll(newQueue);
    }

    public Set<String> getConnectedAccountsIds() {
        return connectedAccountsIds;
    }

    public void incrementStep() {
        currentStep++;
    }

    public Boolean isGameFinished() {
        Boolean gameFinished = false;
        for (Map.Entry<String, List<HeroHolder>> entry : battleHeroes.entrySet()) {
            if (entry.getValue().stream().filter(HeroHolder::isAlive).count() == 0) {
                gameFinished = true;
            }
        }
        return gameFinished;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public Map<String, List<HeroHolder>> getMulliganHeroes() {
        return mulliganHeroes;
    }

    public List<HeroHolder> getBattleHeroes(String senderId, Boolean queryForOpponentHeroes) {
        if (!queryForOpponentHeroes) {
            return battleHeroes.get(senderId);
        }
        for (Map.Entry<String, List<HeroHolder>> entry : battleHeroes.entrySet()) {
            if (!Objects.equals(senderId, entry.getKey())) {
                return entry.getValue();
            }
        }
        throw Exceptions.produceIllegalState(LOG, "Battle heroes does not contain enemy for player {0}", senderId);
    }

    public Queue<HeroHolder> getQueue() {
        return queue;
    }

    public List<StepLogHolder> getBattleLog() {
        return battleLog;
    }
}
