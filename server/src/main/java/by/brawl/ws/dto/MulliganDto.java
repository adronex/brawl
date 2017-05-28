package by.brawl.ws.dto;

import by.brawl.util.Mappers;
import by.brawl.ws.holder.HeroHolder;
import by.brawl.ws.holder.SpellHolder;

import java.util.*;

public class MulliganDto extends AbstractDto implements JsonDto {
	private List<HeroDto> myHeroes = new ArrayList<>();
	private List<HeroDto> enemyHeroes = new ArrayList<>();
	private Map<String, List<String>> heroSpellsIds = new HashMap<>();

	public MulliganDto(Map<String, List<HeroHolder>> mulliganHeroes,
					   String receiverName) {
		mulliganHeroes.forEach((key, value) -> {
			if (key.equals(receiverName)) {
                myHeroes = Mappers.asList(value, HeroDto::new);
            } else {
                enemyHeroes = Mappers.asList(value, HeroDto::new);
            }
        });

		mulliganHeroes.forEach((key, value) -> {
			if (Objects.equals(key, receiverName)) {
				for (HeroHolder heroHolder : mulliganHeroes.get(receiverName)) {
					heroSpellsIds.put(
							heroHolder.getId(),
                            Mappers.asList(heroHolder.getAllSpells(), SpellHolder::getId)
                    );
                }
            }
		});
	}

	public List<HeroDto> getMyHeroes() {
		return myHeroes;
	}

	public List<HeroDto> getEnemyHeroes() {
		return enemyHeroes;
	}

	public Map<String, List<String>> getHeroSpellsIds() {
		return heroSpellsIds;
	}
}
