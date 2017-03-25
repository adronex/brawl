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
public class Character extends NamedEntity {

    @ManyToOne
    private Account owner;

    @ManyToMany
    @JoinTable(name = "character_spell",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns= @JoinColumn(name = "spell_id"))
    private Set<Spell> spells = new LinkedHashSet<>();
}
