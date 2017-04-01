package by.brawl.ws.dto;

import java.util.Objects;

public class SpellDto extends AbstractDto {
    private String id;
    private String heroId;
    private Boolean exposed = false;

    public SpellDto(String id, String heroId) {
        this.id = id;
        this.heroId = heroId;
    }

    public String getId() {
        return id;
    }

    public String getHeroId() {
        return heroId;
    }

    public Boolean getExposed() {
        return exposed;
    }

    public void setExposed(Boolean exposed) {
        this.exposed = exposed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellDto spellDto = (SpellDto) o;
        return Objects.equals(id, spellDto.id) &&
                Objects.equals(heroId, spellDto.heroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heroId);
    }
}
