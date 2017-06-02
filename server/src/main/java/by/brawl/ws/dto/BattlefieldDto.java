package by.brawl.ws.dto;

import by.brawl.util.Mappers;
import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.GameState;

import java.util.*;
import java.util.stream.Collectors;

public class BattlefieldDto extends AbstractDto implements JsonDto {
	private List<HeroDto> myHeroes = new ArrayList<>();
	private List<HeroDto> enemyHeroes = new ArrayList<>();
	private Map<String, Set<SpellDto>> heroSpells = new HashMap<>();
	private Queue<HeroInQueueDto> queue = new LinkedList<>();
	private Integer currentStep;
	private List<StepLogDto> battleLog = new ArrayList<>();
	private GameState gameState;

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
			Boolean enemy = !myHeroesIds.contains(s.getId());
			Boolean exposed = !enemy ||
					battlefieldHolder.getBattleLog().stream()
							.filter(stepLog -> Objects.equals(stepLog.getCasterId(), s.getId()))
							.count() > 0;
			if (exposed) {
				queue.add(new HeroInQueueDto(s.getId(), enemy));
			} else {
				queue.add(new HeroInQueueDto(null, enemy));
			}
		});

		currentStep = battlefieldHolder.getCurrentStep();
        battleLog = Mappers.asList(battlefieldHolder.getBattleLog(), (stepLog -> new StepLogDto(stepLog, receiverName)));
		gameState = battlefieldHolder.getGameState();
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

	public Queue<HeroInQueueDto> getQueue() {
		return queue;
	}

	public Integer getCurrentStep() {
		return currentStep;
	}

	public List<StepLogDto> getBattleLog() {
		return battleLog;
	}

	public GameState getGameState() {
		return gameState;
	}
}
