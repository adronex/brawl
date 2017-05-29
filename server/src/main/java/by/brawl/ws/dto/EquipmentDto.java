package by.brawl.ws.dto;

import by.brawl.entity.bodypart.EquipmentCategory;
import by.brawl.entity.bodypart.EquipmentType;
import by.brawl.ws.holder.EquipmentHolder;

public class EquipmentDto {

	private String name;
	private EquipmentCategory category;
	private EquipmentType type;

	public EquipmentDto(EquipmentHolder equipment) {
		name = equipment.getName();
		category = equipment.getCategory();
		type = equipment.getType();
	}

	public String getName() {
		return name;
	}

	public EquipmentCategory getCategory() {
		return category;
	}

	public EquipmentType getType() {
		return type;
	}
}
