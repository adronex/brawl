package by.brawl.ws.dto;

import by.brawl.entity.Account;
import by.brawl.entity.Hero;
import by.brawl.ws.pojo.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class GameTurnDto extends AbstractDto {

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