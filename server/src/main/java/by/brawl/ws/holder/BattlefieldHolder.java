package by.brawl.ws.holder;

import by.brawl.entity.Squad;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class BattlefieldHolder {

    private GameState gameState = GameState.NOT_STARTED;

    private Map<String, List<HeroHolder>> mulliganHeroes = new HashMap<>();
    private Map<String, List<HeroHolder>> battleHeroes = new HashMap<>();
 //   private Map<String, Set<Spell>> heroSpells = new HashMap<>();
    private Set<String> connectedAccountsIds = new HashSet<>();
    private Queue<HeroHolder> queue = new LinkedList<>();

    public void addSquad(Squad squad) {
        List<HeroHolder> heroes = squad.getHeroes()
                .stream()
                .map(HeroHolder::new)
                .collect(Collectors.toList());
        mulliganHeroes.put(squad.getOwner().getUsername(), heroes);
        connectedAccountsIds.add(squad.getOwner().getUsername());

//        squad.getHeroes().forEach(h ->
//                heroSpells.put(h.getId(), h.getSpells())
//        );
    }

    public void prepareGame() {
		for (List<HeroHolder> heroes : battleHeroes.values()) {
			heroes.forEach(h -> queue.add(h));
		}
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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Map<String, List<HeroHolder>> getMulliganHeroes() {
        return mulliganHeroes;
    }

    public Map<String, List<HeroHolder>> getBattleHeroes() {
        return battleHeroes;
    }

//    public Map<String, Set<Spell>> getHeroSpells() {
//        return heroSpells;
//    }

    public Queue<HeroHolder> getQueue() {
        return queue;
    }
}
