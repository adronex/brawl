package by.brawl.entity.bodypart;

import by.brawl.entity.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Equipment extends NamedEntity {

    @Enumerated(EnumType.STRING)
    private EquipmentType type;
    @Enumerated(EnumType.STRING)
    private EquipmentCategory category;
    private Integer requiredStrength;
    private Integer requiredAgility;
    private Integer requiredEnergy;

    public EquipmentType getType() {
        return type;
    }

    public EquipmentCategory getCategory() {
        return category;
    }

    public Integer getRequiredStrength() {
        return requiredStrength;
    }

    public Integer getRequiredAgility() {
        return requiredAgility;
    }

    public Integer getRequiredEnergy() {
        return requiredEnergy;
    }
}
