package by.brawl.ws.spell;

import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.HeroHolder;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SelfHeal implements SpellLogic {
	private String id = "2";
	private Boolean targetable = false;

	@Override
	public BattlefieldHolder cast(BattlefieldHolder battlefieldHolder,
								  String senderId,
								  Integer victimPosition,
								  Boolean forEnemy) {
		List<HeroHolder> heroes = battlefieldHolder.getBattleHeroes(senderId, false);

		for (HeroHolder hero : heroes) {
			if (Objects.equals(senderId, hero.getId())) {
				hero.heal(8);
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
		return null;
	}

	@Override
	public Set<Integer> getEnemyTargets() {
		return null;
	}
}
