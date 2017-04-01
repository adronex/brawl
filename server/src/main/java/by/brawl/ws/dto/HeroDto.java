package by.brawl.ws.dto;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HeroDto extends AbstractDto {
    private String name;
    private Integer health;
    private Boolean enemy;

    public HeroDto(Hero hero, Account receiver) {
        name = hero.getName();
        enemy = !hero.getOwner().equals(receiver);
        health = hero.getHealth();
    }

    public String getName() {
        return name;
    }

    public Integer getHealth() {
        return health;
    }

    public Boolean getEnemy() {
        return enemy;
    }
}
