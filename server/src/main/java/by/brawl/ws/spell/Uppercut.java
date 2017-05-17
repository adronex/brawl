package by.brawl.ws.spell;

import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.HeroHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Uppercut implements SpellLogic {
	private String id = "6";
	private Boolean targetable = true;
	private Set<Integer> myTargets = new HashSet<>();
	private Set<Integer> enemyTargets = new HashSet<>(Arrays.asList(1));

	@Override
	public BattlefieldHolder cast(BattlefieldHolder battlefieldHolder,
								  String senderId,
								  Integer victimPosition,
								  Boolean forEnemy) {

		List<HeroHolder> sourceList = battlefieldHolder.getBattleHeroes(senderId, true);
		LinkedList<HeroHolder> modifiedHeroesList = new LinkedList<>(sourceList);
		HeroHolder movingHero = modifiedHeroesList.get(4);
		modifiedHeroesList.addFirst(movingHero);
		modifiedHeroesList.removeLast();
		sourceList.clear();
		sourceList.addAll(modifiedHeroesList);
		return battlefieldHolder;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Boolean getTargetable() {
		return targetable;
	}

	@Override
	public Set<Integer> getMyTargets() {
		return myTargets;
	}

	@Override
	public Set<Integer> getEnemyTargets() {
		return enemyTargets;
	}
}
