package by.brawl.ws.pojo;

import by.brawl.entity.Hero;

public class HeroHolder {
    private String id;
    private Integer health = 30;

    public HeroHolder(Hero hero) {
        id = hero.getId();
        health = hero.getHealth();
    }

    public void hit(Integer value) {
        health -= value;
    }

    public Boolean isAlive() {
        return health > 0;
    }

    public String getId() {
        return id;
    }

    public Integer getHealth() {
        return health;
    }
}
