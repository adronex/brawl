package by.brawl.dto;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class NewHeroDto {

    private String id;
    private String name;
    private Set<ExposableSpellDto> spells = new LinkedHashSet<>();

    public NewHeroDto(Hero hero) {
        id = hero.getId();
        name = hero.getName();
        spells = hero.getSpells().stream()
                .map(ExposableSpellDto::new)
                .collect(Collectors.toSet());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<ExposableSpellDto> getSpells() {
        return spells;
    }
}
