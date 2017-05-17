package by.brawl.ws.spell;

import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.HeroHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EldrichPull implements SpellLogic {
	private String id = "5";
	private Boolean targetable = true;
	private Set<Integer> myTargets = new HashSet<>();
	private Set<Integer> enemyTargets = new HashSet<>(Arrays.asList(4));

	@Override
	public BattlefieldHolder cast(BattlefieldHolder battlefieldHolder,
								  String senderId,
								  Integer victimPosition,
								  Boolean forEnemy) {
		List<HeroHolder> heroes = battlefieldHolder.getBattleHeroes(senderId, true);
		HeroHolder movingHero = heroes.get(0);
		heroes.add(movingHero);
		heroes.remove(0);
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
