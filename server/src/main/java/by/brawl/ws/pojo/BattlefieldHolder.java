package by.brawl.ws.pojo;

import by.brawl.entity.Squad;

import java.util.*;
import java.util.stream.Collectors;

public class BattlefieldHolder {

    private Map<String, List<HeroHolder>> mulliganHeroes = new HashMap<>();
    private Map<String, List<HeroHolder>> battleHeroes = new HashMap<>();
  //  private Map<String, String> spells = new HashMap<>();
    private Queue<String> queue = new LinkedList<>();

    public void addSquad(Squad squad) {
        List<HeroHolder> heroes = squad.getHeroes()
                .stream()
                .map(HeroHolder::new)
                .collect(Collectors.toList());
        mulliganHeroes.put(squad.getOwner().getUsername(), heroes);

//        squad.getHeroes().forEach(h ->
//                h.getSpells().forEach(s -> spells.put(h.getId(), s.getId()))
//        );
    }

    public void prepareGame() {
        mulliganHeroes.clear();
        setQueue();
    }


    private void setQueue() {
        for (List<HeroHolder> heroes : battleHeroes.values()) {
            heroes.forEach(h -> queue.add(h.getId()));
        }
    }

    public Map<String, List<HeroHolder>> getMulliganHeroes() {
        return mulliganHeroes;
    }

    public Map<String, List<HeroHolder>> getBattleHeroes() {
        return battleHeroes;
    }

//    public Map<String, String> getSpells() {
//        return spells;
//    }

    public Queue<String> getQueue() {
        return queue;
    }
}
