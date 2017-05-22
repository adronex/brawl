package by.brawl.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEntity extends IdEntity {

    protected String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NamedEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
