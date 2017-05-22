package by.brawl.ws.dto;

import by.brawl.entity.bodypart.BodypartType;
import by.brawl.ws.holder.BodypartHolder;

public class BodypartDto {

    private String id;
    private BodypartType type;

    public BodypartDto(BodypartHolder holder) {
        id = holder.getId();
        type = holder.getType();
    }

    public String getId() {
        return id;
    }

    public BodypartType getType() {
        return type;
    }
}
