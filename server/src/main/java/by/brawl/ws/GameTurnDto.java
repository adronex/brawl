package by.brawl.ws;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameTurnDto {
    private GameState gameState;
    private List<HeroDto> heroesQueue = new ArrayList<>();
    private Boolean winner;

    public GameTurnDto(GameState gameState, Queue<Hero> heroesQueue, Account receiver) {
        this.gameState = gameState;
        this.heroesQueue = heroesQueue.stream().map(h -> new HeroDto(h, receiver)).collect(Collectors.toList());

        if (GameState.END.equals(gameState)) {
            winner = this.heroesQueue.stream().filter(h -> !h.getEnemy()).count() > 0;
        }
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

    public Boolean getWinner() {
        return winner;
    }
}
