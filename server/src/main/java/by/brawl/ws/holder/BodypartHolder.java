package by.brawl.ws.holder;

import by.brawl.entity.bodypart.Bodypart;
import by.brawl.entity.bodypart.BodypartType;

public class BodypartHolder {
    private String id;
    private BodypartType type;

    public BodypartHolder(Bodypart bodypart) {
        id = bodypart.getId();
        type = bodypart.getType();

    }

    public String getId() {
        return id;
    }

    public BodypartType getType() {
        return type;
    }
}
