package by.brawl.dto;

import by.brawl.entity.Account;
import by.brawl.entity.Squad;
import by.brawl.ws.dto.AbstractDto;
import by.brawl.ws.dto.JsonDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MulliganDto implements JsonDto {

    private Set<NewHeroDto> myHeroes = new LinkedHashSet<>();
    private Set<NewHeroDto> enemyHeroes = new LinkedHashSet<>();

    public MulliganDto(Squad squad1, Squad squad2, Account receiver) {

        if (receiver.equals(squad1.getOwner())) {

            myHeroes = squad1.getHeroes().stream().map(NewHeroDto::new).collect(Collectors.toSet());
            enemyHeroes = squad2.getHeroes().stream().map(NewHeroDto::new).collect(Collectors.toSet());

        } else if (receiver.equals(squad2.getOwner())){

            myHeroes = squad2.getHeroes().stream().map(NewHeroDto::new).collect(Collectors.toSet());
            enemyHeroes = squad1.getHeroes().stream().map(NewHeroDto::new).collect(Collectors.toSet());

        } else {
            throw new IllegalStateException();
        }
    }

    @JsonIgnore
    @Override
    public String asJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public Set<NewHeroDto> getMyHeroes() {
        return myHeroes;
    }

    public Set<NewHeroDto> getEnemyHeroes() {
        return enemyHeroes;
    }
}
