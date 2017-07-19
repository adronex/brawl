package by.brawl.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Squad extends NamedEntity {

    @ManyToOne
    private Account owner;

    @ManyToMany
    @JoinTable(name = "hero_squad",
            joinColumns = @JoinColumn(name = "squad_id"),
            inverseJoinColumns= @JoinColumn(name = "hero_id"))
    private Set<Hero> heroes = new LinkedHashSet<>();

    public Account getOwner() {
        return owner;
    }

    public Set<Hero> getHeroes() {
        return heroes;
    }
}
