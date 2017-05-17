package by.brawl.ws.spell;

import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.HeroHolder;

import java.util.List;
import java.util.Set;

public class ShootThemAll implements SpellLogic {
	private String id = "7";
	private Boolean targetable = false;

	@Override
	public BattlefieldHolder cast(BattlefieldHolder battlefieldHolder,
								  String senderId,
								  Integer victimPosition,
								  Boolean forEnemy) {
		List<HeroHolder> heroes = battlefieldHolder.getBattleHeroes(senderId, true);

		for (HeroHolder hero : heroes) {
			hero.hit(4);
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
