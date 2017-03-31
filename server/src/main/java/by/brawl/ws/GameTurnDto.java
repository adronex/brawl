package by.brawl.ws;

import by.brawl.entity.Hero;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class GameTurnDto {
    private GameState gameState;
    private List<HeroDto> heroesQueue = new ArrayList<>();

    public GameTurnDto(GameState gameState, Queue<Hero> heroesQueue) {
        this.gameState = gameState;
        this.heroesQueue = heroesQueue.stream().map(HeroDto::new).collect(Collectors.toList());
    }

    @JsonIgnore
    public String asJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "hui";
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<HeroDto> getHeroesQueue() {
        return heroesQueue;
    }
}
