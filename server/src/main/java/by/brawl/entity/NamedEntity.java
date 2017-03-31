package by.brawl.entity;

import javax.persistence.MappedSuperclass;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 25.03.2017.
 */
@MappedSuperclass
public class NamedEntity extends IdEntity {

    protected String name;

    @Override
    public String toString() {
        return "NamedEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
