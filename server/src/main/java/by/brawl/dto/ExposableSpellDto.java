package by.brawl.dto;

import by.brawl.entity.Spell;

public class ExposableSpellDto {

    private String id;
    private String name;

    public ExposableSpellDto(Spell spell) {
        id = spell.getId();
        name = spell.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
