package by.brawl.dto;

import by.brawl.entity.Squad;

public class SquadToChooseDto {
    private String id;
    private String name;

    public SquadToChooseDto(Squad squad) {
        id = squad.getId();
        name = squad.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
