package by.brawl.ws.pojo;

import by.brawl.entity.Hero;

public class HeroHolder {
    private String id;
    private Integer health;

    public HeroHolder(Hero hero) {
        id = hero.getId();
        health = hero.getHealth();
    }

    public String getId() {
        return id;
    }

    public Integer getHealth() {
        return health;
    }
}
