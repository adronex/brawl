package by.brawl.ws.holder;

import by.brawl.entity.bodypart.Bodypart;

import java.util.Set;

public class HeroAttributesHolder {
    private AttributeHolder health;
    private AttributeHolder armor;
    private AttributeHolder evasion;
    private AttributeHolder accuracy;
    private AttributeHolder damage;
    private AttributeHolder speed;
    private AttributeHolder criticalDamage;
    private AttributeHolder criticalChance;

    public HeroAttributesHolder(Set<Bodypart> bodyparts) {
        Integer strength = 0;
        Integer agility = 0;
        Integer energy = 0;
        for (Bodypart bodypart : bodyparts) {
            strength += bodypart.getStrength();
            agility += bodypart.getAgility();
            energy += bodypart.getEnergy();
        }
        health = new AttributeHolder(20);
        armor = new AttributeHolder(strength);
        evasion = new AttributeHolder(agility);
        accuracy = new AttributeHolder(100);
        damage = new AttributeHolder(100);
        speed = new AttributeHolder(energy);
        criticalDamage = new AttributeHolder(200);
        criticalChance = new AttributeHolder(15);
    }

    public AttributeHolder getHealth() {
        return health;
    }

    public AttributeHolder getArmor() {
        return armor;
    }

    public AttributeHolder getEvasion() {
        return evasion;
    }

    public AttributeHolder getAccuracy() {
        return accuracy;
    }

    public AttributeHolder getDamage() {
        return damage;
    }

    public AttributeHolder getSpeed() {
        return speed;
    }

    public AttributeHolder getCriticalDamage() {
        return criticalDamage;
    }

    public AttributeHolder getCriticalChance() {
        return criticalChance;
    }
}
