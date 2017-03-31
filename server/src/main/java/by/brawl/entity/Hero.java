package by.brawl.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 25.03.2017.
 */
@Entity
public class Hero extends NamedEntity implements Comparable {

    @ManyToOne
    private Account owner;

    @ManyToMany
    @JoinTable(name = "hero_spell",
            joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns= @JoinColumn(name = "spell_id"))
    private Set<Spell> spells = new LinkedHashSet<>();

    public Account getOwner() {
        return owner;
    }

    public Set<Spell> getSpells() {
        return spells;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                ", spells=" + spells +
                '}';
    }
}
