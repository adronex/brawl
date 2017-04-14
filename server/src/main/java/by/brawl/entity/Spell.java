package by.brawl.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 25.03.2017.
 */
@Entity
public class Spell extends NamedEntity {
    @Transient
    private Set<Integer> myTargets = new HashSet<>();
    @Transient
    private Set<Integer> enemyTargets = new HashSet<>();

    public Set<Integer> getMyTargets() {
        return myTargets;
    }

    public Set<Integer> getEnemyTargets() {
        return enemyTargets;
    }
}
