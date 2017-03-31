package by.brawl.ws;

import by.brawl.entity.Hero;

public class HeroDto {
    private String id;
    private String name;
    private String owner;

    public HeroDto(Hero hero) {
        id = hero.getId();
        name = hero.getName();
        owner = hero.getOwner().getUsername();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
