package by.brawl.ws.holder;

import by.brawl.entity.bodypart.Equipment;
import by.brawl.entity.bodypart.EquipmentCategory;
import by.brawl.entity.bodypart.EquipmentType;

public class EquipmentHolder {
    private String id;
    private EquipmentType type;
    private EquipmentCategory category;

    public EquipmentHolder(Equipment equipment) {
        id = equipment.getId();
        type = equipment.getType();
        category = equipment.getCategory();
    }

    public String getId() {
        return id;
    }

    public EquipmentType getType() {
        return type;
    }

    public EquipmentCategory getCategory() {
        return category;
    }
}
