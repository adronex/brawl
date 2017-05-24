package by.brawl.ws.holder;

import by.brawl.entity.Hero;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HeroHolder {
    private String id;
    private HeroAttributesHolder attributes;
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
        attributes = new HeroAttributesHolder(hero.getBodyparts());
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
        attributes.getHealth().incrementCurrent(value);
    }

    public void hit(Integer value) {
        attributes.getHealth().decrementCurrent(value);
    }

    public Boolean isAlive() {
        return attributes.getHealth().getCurrentValue() > 0;
    }

    public String getId() {
        return id;
    }

    public Set<BodypartHolder> getBodyparts() {
        return bodyparts;
    }
}
