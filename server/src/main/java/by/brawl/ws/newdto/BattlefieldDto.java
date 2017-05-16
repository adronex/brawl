package by.brawl.ws.newdto;

import by.brawl.ws.holder.BattlefieldHolder;

import java.util.*;
import java.util.stream.Collectors;

public class BattlefieldDto extends AbstractDto implements JsonDto {
    private List<HeroDto> myHeroes = new ArrayList<>();
    private List<HeroDto> enemyHeroes = new ArrayList<>();
    private Map<String, Set<SpellDto>> heroSpells = new HashMap<>();
    private Queue<String> queue = new LinkedList<>();

    public BattlefieldDto(BattlefieldHolder battlefieldHolder,
                          String receiverName) {
        battlefieldHolder.getBattleHeroes().forEach((key, value) -> {
            if (key.equals(receiverName)) {
                myHeroes = value.stream()
                        .map(HeroDto::new)
                        .collect(Collectors.toList());
            } else {
                enemyHeroes = value.stream()
                        .map(HeroDto::new)
                        .collect(Collectors.toList());
            }
        });

        List<String> myHeroesIds = myHeroes.stream()
                .map(HeroDto::getId)
                .collect(Collectors.toList());

        battlefieldHolder.getHeroSpells().forEach((key, value) -> {
            if (myHeroesIds.contains(key)) {
                heroSpells.put(key, value.stream().map(SpellDto::new).collect(Collectors.toSet()));
            }
        });

        battlefieldHolder.getQueue().forEach(s -> {
            if (myHeroesIds.contains(s)) {
                queue.add(s);
            } else {
                queue.add(null);
            }
        });
    }

    public List<HeroDto> getMyHeroes() {
        return myHeroes;
    }

    public List<HeroDto> getEnemyHeroes() {
        return enemyHeroes;
    }

    public Map<String, Set<SpellDto>> getHeroSpells() {
        return heroSpells;
    }

    public Queue<String> getQueue() {
        return queue;
    }
}
