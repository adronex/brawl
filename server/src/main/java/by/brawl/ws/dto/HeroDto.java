package by.brawl.ws.dto;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;

public class HeroDto {
    private String id;
    private String name;
    private Boolean enemy;
    private Integer health;

    public HeroDto(Hero hero, Account receiver) {
        id = hero.getId();
        name = hero.getName();
        enemy = !hero.getOwner().equals(receiver);
        health = hero.getHealth();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getEnemy() {
        return enemy;
    }

    public Integer getHealth() {
        return health;
    }
}
