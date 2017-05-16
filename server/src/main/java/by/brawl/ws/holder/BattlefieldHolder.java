package by.brawl.ws.holder;

import by.brawl.entity.Squad;

import java.util.*;
import java.util.stream.Collectors;

public class BattlefieldHolder {

    private GameState gameState = GameState.NOT_STARTED;
    private Integer currentStep = 0;

    private Map<String, List<HeroHolder>> mulliganHeroes = new HashMap<>();
    private Map<String, List<HeroHolder>> battleHeroes = new HashMap<>();
    private Set<String> connectedAccountsIds = new HashSet<>();
    private Queue<HeroHolder> queue = new LinkedList<>();
    private List<StepLogHolder> battleLog = new ArrayList<>();

    public void addSquad(Squad squad) {
        List<HeroHolder> heroes = squad.getHeroes()
                .stream()
                .map(HeroHolder::new)
                .collect(Collectors.toList());
        mulliganHeroes.put(squad.getOwner().getUsername(), heroes);
        connectedAccountsIds.add(squad.getOwner().getUsername());
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

    public Map<String, List<HeroHolder>> getBattleHeroes() {
        return battleHeroes;
    }

    public Queue<HeroHolder> getQueue() {
        return queue;
    }

    public List<StepLogHolder> getBattleLog() {
        return battleLog;
    }
}
