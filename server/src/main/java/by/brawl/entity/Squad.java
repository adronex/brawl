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
    @JoinTable(name = "hero_squad",
            joinColumns = @JoinColumn(name = "squad_id"),
            inverseJoinColumns= @JoinColumn(name = "hero_id"))
    private Set<Hero> heroes = new LinkedHashSet<>();

    public Set<Hero> getHeroes() {
        return heroes;
    }
}
