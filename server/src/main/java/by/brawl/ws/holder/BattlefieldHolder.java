package by.brawl.ws.holder;

import by.brawl.entity.Spell;
import by.brawl.entity.Squad;
import by.brawl.ws.newdto.AbstractDto;
import by.brawl.ws.newdto.JsonDto;
import by.brawl.ws.spell.SpellLogic;
import by.brawl.ws.spell.SuckerPunch;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class BattlefieldHolder extends AbstractDto implements JsonDto {

    private GameState gameState = GameState.NOT_STARTED;
    private Map<String, SpellLogic> spellsPool = new HashMap<>();

    private Map<String, List<HeroHolder>> mulliganHeroes = new HashMap<>();
    private Map<String, List<HeroHolder>> battleHeroes = new HashMap<>();
    private Map<String, Set<Spell>> heroSpells = new HashMap<>();
    private Set<String> connectedAccountsIds = new HashSet<>();
    private Queue<String> queue = new LinkedList<>();

    public BattlefieldHolder() {
        SpellLogic suckerPunch = new SuckerPunch();
        spellsPool.put(suckerPunch.getId(), suckerPunch);
    }

    public void addSquad(Squad squad) {
        List<HeroHolder> heroes = squad.getHeroes()
                .stream()
                .map(HeroHolder::new)
                .collect(Collectors.toList());
        mulliganHeroes.put(squad.getOwner().getUsername(), heroes);
        connectedAccountsIds.add(squad.getOwner().getUsername());

        squad.getHeroes().forEach(h ->
                heroSpells.put(h.getId(), h.getSpells())
        );
    }

    public void prepareGame() {
        mulliganHeroes.clear();
        prepareQueue();
    }


    private void prepareQueue() {
        for (List<HeroHolder> heroes : battleHeroes.values()) {
            heroes.forEach(h -> queue.add(h.getId()));
        }
    }

    public void moveQueue() {
        String heroToMove = queue.remove();
        queue.add(heroToMove);

        List<String> newQueue = queue.stream()
                .filter(queueElement ->
                        battleHeroes.values()
                                .stream()
                                .flatMap(Collection::stream)
                                .filter(h -> h.getId().equals(queueElement) && h.isAlive())
                                .count() > 0)
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

    public Map<String, SpellLogic> getSpellsPool() {
        return spellsPool;
    }

    public Map<String, List<HeroHolder>> getMulliganHeroes() {
        return mulliganHeroes;
    }

    public Map<String, List<HeroHolder>> getBattleHeroes() {
        return battleHeroes;
    }

    public Map<String, Set<Spell>> getHeroSpells() {
        return heroSpells;
    }

    public Queue<String> getQueue() {
        return queue;
    }
}
