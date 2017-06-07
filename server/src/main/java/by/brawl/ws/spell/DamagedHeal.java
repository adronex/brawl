package by.brawl.ws.spell;

import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.HeroHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DamagedHeal implements SpellLogic {
	private String id = "3";
	private Boolean targetable = true;
	private Set<Integer> myTargets = new HashSet<>(Arrays.asList(1, 2, 3, 4));
	private Set<Integer> enemyTargets = new HashSet<>();

	@Override
	public BattlefieldHolder cast(BattlefieldHolder battlefieldHolder,
								  String senderId,
								  Integer victimPosition,
								  Boolean forEnemy) {
		battlefieldHolder.getBattleHeroes(senderId, false).get(victimPosition).heal(12);
		List<HeroHolder> heroes = battlefieldHolder.getBattleHeroes(senderId, false);

		for (HeroHolder hero : heroes) {
			if (Objects.equals(senderId, hero.getId())) {
				hero.hit(4);
			}
		}
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
