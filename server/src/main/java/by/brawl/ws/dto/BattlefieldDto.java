package by.brawl.ws.dto;

import by.brawl.util.Mappers;
import by.brawl.ws.holder.BattlefieldHolder;

import java.util.*;
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
        myHeroes = Mappers.asList(battlefieldHolder.getBattleHeroes(receiverName, false), HeroDto::new);
        enemyHeroes = Mappers.asList(battlefieldHolder.getBattleHeroes(receiverName, true), HeroDto::new);

		battlefieldHolder.getBattleHeroes(receiverName, false).forEach(heroHolder ->
				heroSpells.put(
						heroHolder.getId(),
						heroHolder.getAllSpells().stream()
								.map(SpellDto::new).collect(Collectors.toSet())
				));
		// todo: lambda is a fuck
		battlefieldHolder.getBattleHeroes(receiverName, true).forEach(heroHolder ->
				heroSpells.put(
						heroHolder.getId(),
						heroHolder.getAllSpells().stream()
								.filter(spellHolder -> battlefieldHolder.getBattleLog().stream()
										.filter(stepLog -> Objects.equals(stepLog.getSpellId(), spellHolder.getId()) && Objects.equals(stepLog.getCasterId(), heroHolder.getId()))
										.count() > 0)
								.map(SpellDto::new).collect(Collectors.toSet())
				));

        List<String> myHeroesIds = Mappers.asList(myHeroes, HeroDto::getId);

		battlefieldHolder.getQueue().forEach(s -> {
			Boolean exposed = myHeroesIds.contains(s.getId()) ||
					battlefieldHolder.getBattleLog().stream()
							.filter(stepLog -> Objects.equals(stepLog.getCasterId(), s.getId()))
							.count() > 0;
			if (exposed) {
				queue.add(s.getId());
			} else {
				queue.add(null);
			}
		});

		currentStep = battlefieldHolder.getCurrentStep();
        battleLog = Mappers.asList(battlefieldHolder.getBattleLog(), (stepLog -> new StepLogDto(stepLog, receiverName)));
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
