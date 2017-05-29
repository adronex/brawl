package by.brawl.ws.dto;

import by.brawl.util.Mappers;
import by.brawl.ws.holder.HeroHolder;

import java.util.LinkedHashSet;
import java.util.Set;

public class HeroDto {
    private String id;
    private Set<BodypartDto> bodyparts = new LinkedHashSet<>();
    private Set<EquipmentDto> equipments = new LinkedHashSet<>();

    public HeroDto(HeroHolder heroHolder) {
        id = heroHolder.getId();
        bodyparts = Mappers.asSet(heroHolder.getBodyparts(), BodypartDto::new);
        equipments = Mappers.asSet(heroHolder.getEquipments(), EquipmentDto::new);
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

    public Set<EquipmentDto> getEquipments() {
        return equipments;
    }
    //    public Set<SpellDto> getSpells() {
//        return spells;
//    }
}
