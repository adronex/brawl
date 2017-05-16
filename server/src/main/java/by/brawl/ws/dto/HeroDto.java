package by.brawl.ws.dto;

import by.brawl.ws.holder.HeroHolder;

public class HeroDto {
    private String id;
  //  private Set<SpellDto> spells = new LinkedHashSet<>();

    public HeroDto(HeroHolder heroHolder) {
        id = heroHolder.getId();
//        spells.addAll(
//                heroHolder.getAllSpells().stream()
//                        .map(SpellDto::new)
//                        .collect(Collectors.toSet())
//        );
    }

    public String getId() {
        return id;
    }

//    public Set<SpellDto> getSpells() {
//        return spells;
//    }
}
