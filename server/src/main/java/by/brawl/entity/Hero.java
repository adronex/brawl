package by.brawl.entity;

import by.brawl.entity.bodypart.Bodypart;

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
//
//    @Transient
//    private Integer health = 30;

//    public void hit(Integer amount) {
//        health -= amount;
//    }
//
//    public Boolean isAlive() {
//        return health > 0;
//    }

    public Account getOwner() {
        return owner;
    }

    public Set<Spell> getSpells() {
        return spells;
    }

    public Set<Bodypart> getBodyparts() {
        return bodyparts;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", owner=" + owner.getUsername() +
                '}';
    }
}
