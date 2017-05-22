package by.brawl.entity.bodypart;

import by.brawl.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Bodypart extends IdEntity {

    @Enumerated(EnumType.STRING)
    private BodypartType type;
    private Integer strength;
    private Integer agility;
    private Integer energy;

    public BodypartType getType() {
        return type;
    }

    public Integer getStrength() {
        return strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public Integer getEnergy() {
        return energy;
    }
}
