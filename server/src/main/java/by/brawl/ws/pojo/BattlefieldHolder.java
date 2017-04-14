package by.brawl.ws.pojo;

import by.brawl.entity.Spell;
import by.brawl.entity.Squad;
import by.brawl.ws.dto.AbstractDto;
import by.brawl.ws.dto.JsonDto;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;

public class BattlefieldHolder extends AbstractDto implements JsonDto {

    @JsonIgnore
    private Map<String, WebSocketSession> sessions = new HashMap<>();
    private Map<String, List<HeroHolder>> mulliganHeroes = new HashMap<>();
    private Map<String, List<HeroHolder>> battleHeroes = new HashMap<>();
    private Map<String, Set<Spell>> heroSpells = new HashMap<>();
    private Queue<String> queue = new LinkedList<>();

    public void addSquad(WebSocketSession session, Squad squad) {
        List<HeroHolder> heroes = squad.getHeroes()
                .stream()
                .map(HeroHolder::new)
                .collect(Collectors.toList());
        mulliganHeroes.put(squad.getOwner().getUsername(), heroes);
        sessions.put(session.getPrincipal().getName(), session);

        squad.getHeroes().forEach(h ->
                heroSpells.put(h.getId(), h.getSpells())
        );
    }

    public void prepareGame() {
        mulliganHeroes.clear();
        setQueue();
    }


    private void setQueue() {
        for (List<HeroHolder> heroes : battleHeroes.values()) {
            heroes.forEach(h -> queue.add(h.getId()));
        }
    }

    public Map<String, WebSocketSession> getSessions() {
        return sessions;
    }

    public Map<String, List<HeroHolder>> getMulliganHeroes() {
        return mulliganHeroes;
    }

    public Map<String, List<HeroHolder>> getBattleHeroes() {
        return battleHeroes;
    }

    public Map<String, Set<Spell>> getHeroSpells() {
        return heroSpells;
    }

    public Queue<String> getQueue() {
        return queue;
    }
}
