package by.brawl.ws.newdto;

import by.brawl.ws.holder.HeroHolder;

public class HeroDto {
    private String id;

    public HeroDto(HeroHolder heroHolder) {
        id = heroHolder.getId();
    }

    public String getId() {
        return id;
    }
}
