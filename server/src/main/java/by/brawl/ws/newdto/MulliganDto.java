package by.brawl.ws.newdto;

import by.brawl.entity.Spell;
import by.brawl.ws.holder.HeroHolder;

import java.util.*;
import java.util.stream.Collectors;

public class MulliganDto extends AbstractDto implements JsonDto {
    private List<HeroDto> myHeroes = new ArrayList<>();
    private List<HeroDto> enemyHeroes = new ArrayList<>();
    private Map<String, Set<SpellDto>> heroSpells = new HashMap<>();

    public MulliganDto(Map<String, List<HeroHolder>> mulliganHeroes,
                       Map<String, Set<Spell>> heroSpells,
                       String receiverName) {
        mulliganHeroes.forEach((key, value) -> {
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

        heroSpells.forEach((key, value) -> {
            if (myHeroesIds.contains(key)) {
                this.heroSpells.put(key, value.stream().map(SpellDto::new).collect(Collectors.toSet()));
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
}
