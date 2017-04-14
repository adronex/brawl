package by.brawl.ws.newdto;

import by.brawl.ws.pojo.HeroHolder;

public class HeroDto {
    private String id;

    public HeroDto(HeroHolder heroHolder) {
        id = heroHolder.getId();
    }

    public String getId() {
        return id;
    }
}
