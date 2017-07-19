package by.brawl.entity;

import by.brawl.entity.bodypart.Bodypart;
import by.brawl.entity.bodypart.Equipment;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Hero extends NamedEntity {

    @ManyToOne
    private Account owner;

    @ManyToMany
    @JoinTable(name = "hero_spell",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns= @JoinColumn(name = "spell_id"))
    private Set<Spell> spells = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "hero_bodypart",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "bodypart_id"))
    private Set<Bodypart> bodyparts = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "hero_equipment",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private Set<Equipment> equipments = new LinkedHashSet<>();

    public Account getOwner() {
        return owner;
    }

    public Set<Spell> getSpells() {
        return spells;
    }

    public Set<Bodypart> getBodyparts() {
        return bodyparts;
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + getName() + '\'' +
                ", owner=" + owner.getUsername() +
                '}';
    }
}
