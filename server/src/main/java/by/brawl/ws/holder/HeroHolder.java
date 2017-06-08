package by.brawl.ws.holder;

import by.brawl.entity.Hero;
import by.brawl.util.Mappers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HeroHolder {
    private String id;
    private Set<SpellHolder> spells = new LinkedHashSet<>();
    private Set<BodypartHolder> bodyparts = new LinkedHashSet<>();
    private Set<EquipmentHolder> equipments = new LinkedHashSet<>();
    private HeroAttributesHolder attributes;

    public HeroHolder(Hero hero) {
        id = hero.getId();
        spells = Mappers.asSet(hero.getSpells(), SpellHolder::new);
        bodyparts = Mappers.asSet(hero.getBodyparts(), BodypartHolder::new);
        equipments = Mappers.asSet(hero.getEquipments(), EquipmentHolder::new);
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
        return attributes.getHealth().getCurrent() > 0;
    }

    public String getId() {
        return id;
    }

    public Set<BodypartHolder> getBodyparts() {
        return bodyparts;
    }

    public Set<EquipmentHolder> getEquipments() {
        return equipments;
    }

    public HeroAttributesHolder getAttributes() {
        return attributes;
    }
}
