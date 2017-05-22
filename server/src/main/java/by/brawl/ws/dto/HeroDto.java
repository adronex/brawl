package by.brawl.ws.dto;

import by.brawl.ws.holder.HeroHolder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HeroDto {
    private String id;
    private Set<BodypartDto> bodyparts = new LinkedHashSet<>();

    public HeroDto(HeroHolder heroHolder) {
        id = heroHolder.getId();
        bodyparts.addAll(
                heroHolder.getBodyparts().stream()
                        .map(BodypartDto::new)
                        .collect(Collectors.toList())
        );
//        spells.addAll(
//                heroHolder.getAllSpells().stream()
//                        .map(SpellDto::new)
//                        .collect(Collectors.toSet())
//        );
    }

    public String getId() {
        return id;
    }

    public Set<BodypartDto> getBodyparts() {
        return bodyparts;
    }
//    public Set<SpellDto> getSpells() {
//        return spells;
//    }
}
