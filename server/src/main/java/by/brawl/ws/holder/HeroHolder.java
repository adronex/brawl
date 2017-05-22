package by.brawl.ws.holder;

import by.brawl.entity.Hero;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HeroHolder {
    private String id;
    private Integer health = 30;
    private Set<SpellHolder> spells = new LinkedHashSet<>();
    private Set<BodypartHolder> bodyparts = new LinkedHashSet<>();

    public HeroHolder(Hero hero) {
        id = hero.getId();
        spells.addAll(
                hero.getSpells().stream()
                        .map(SpellHolder::new)
                        .collect(Collectors.toList())
        );
        bodyparts.addAll(
                hero.getBodyparts().stream()
                        .map(BodypartHolder::new)
                        .collect(Collectors.toList())
        );
    }

    public Set<SpellHolder> getAvailableSpells() {
        return spells.stream()
                .filter(SpellHolder::isAvailable)
                .collect(Collectors.toSet());
    }

    public Set<SpellHolder> getAllSpells() {
        return spells;
    }

    public void heal(Integer value) {
        health += value;
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

    public Set<BodypartHolder> getBodyparts() {
        return bodyparts;
    }
}
