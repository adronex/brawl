package by.brawl.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 25.03.2017.
 */
@Entity
public class Squad extends NamedEntity {

    @ManyToOne
    private Account owner;

    @ManyToMany
    @JoinTable(name = "character_squad",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns= @JoinColumn(name = "squad_id"))
    private Set<Character> characters = new LinkedHashSet<>();
}
