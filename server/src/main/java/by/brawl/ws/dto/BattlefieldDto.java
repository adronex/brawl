package by.brawl.ws.dto;

import by.brawl.ws.holder.BattlefieldHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class BattlefieldDto extends AbstractDto implements JsonDto {
	private List<HeroDto> myHeroes = new ArrayList<>();
	private List<HeroDto> enemyHeroes = new ArrayList<>();
	private Map<String, Set<SpellDto>> heroSpells = new HashMap<>();
	private Queue<String> queue = new LinkedList<>();
    private Integer currentStep;
    private List<StepLogDto> battleLog = new ArrayList<>();

	public BattlefieldDto(BattlefieldHolder battlefieldHolder,
						  String receiverName) {
		myHeroes = battlefieldHolder.getBattleHeroes(receiverName, false).stream()
				.map(HeroDto::new).collect(Collectors.toList());
		enemyHeroes = battlefieldHolder.getBattleHeroes(receiverName, true).stream()
				.map(HeroDto::new).collect(Collectors.toList());

		battlefieldHolder.getBattleHeroes(receiverName, false).forEach(heroHolder ->
				heroSpells.put(
						heroHolder.getId(),
						heroHolder.getAllSpells().stream()
								.map(SpellDto::new).collect(Collectors.toSet())
				));

		List<String> myHeroesIds = myHeroes.stream()
				.map(HeroDto::getId)
				.collect(Collectors.toList());

		battlefieldHolder.getQueue().forEach(s -> {
			if (myHeroesIds.contains(s.getId())) {
				queue.add(s.getId());
			} else {
				queue.add(null);
			}
		});

        currentStep = battlefieldHolder.getCurrentStep();
        // todo: lambdas are bitches!
        battleLog.addAll(battlefieldHolder.getBattleLog().stream().map(log -> new StepLogDto(log, receiverName)).collect(Collectors.toList()));
    }

	public List<HeroDto> getMyHeroes() {
		return myHeroes;
	}

	public List<HeroDto> getEnemyHeroes() {
		return enemyHeroes;
	}

	public Map<String, Set<SpellDto>> getHeroSpells() {
		return heroSpells;
	}

	public Queue<String> getQueue() {
		return queue;
	}

    public Integer getCurrentStep() {
        return currentStep;
    }

    public List<StepLogDto> getBattleLog() {
        return battleLog;
    }
}
