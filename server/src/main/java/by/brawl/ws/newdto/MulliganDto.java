package by.brawl.ws.newdto;

import by.brawl.ws.holder.HeroHolder;
import by.brawl.ws.holder.SpellHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MulliganDto extends AbstractDto implements JsonDto {
	private List<HeroDto> myHeroes = new ArrayList<>();
	private List<HeroDto> enemyHeroes = new ArrayList<>();
	private Map<String, List<String>> heroSpellsIds = new HashMap<>();

	public MulliganDto(Map<String, List<HeroHolder>> mulliganHeroes,
					   String receiverName) {
		mulliganHeroes.forEach((key, value) -> {
			if (key.equals(receiverName)) {
				myHeroes = value.stream()
						.map(HeroDto::new)
						.collect(Collectors.toList());
			} else {
				enemyHeroes = value.stream()
						.map(HeroDto::new)
						.collect(Collectors.toList());
			}
		});

		mulliganHeroes.forEach((key, value) -> {
			if (Objects.equals(key, receiverName)) {
				for (HeroHolder heroHolder : mulliganHeroes.get(receiverName)) {
					heroSpellsIds.put(
							heroHolder.getId(),
							heroHolder.getAllSpells().stream()
									.map(SpellHolder::getId)
									.collect(Collectors.toList())
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
