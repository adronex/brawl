package by.brawl.ws.newdto;

import by.brawl.entity.Spell;

public class SpellDto {
    private String id;

    public SpellDto(Spell spell) {
        id = spell.getId();
    }

    public String getId() {
        return id;
    }
}
