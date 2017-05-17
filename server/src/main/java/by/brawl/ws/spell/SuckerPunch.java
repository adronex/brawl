package by.brawl.ws.spell;

import by.brawl.ws.holder.BattlefieldHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SuckerPunch implements SpellLogic {
    private String id = "1";
    private Boolean targetable = true;
    private Set<Integer> myTargets = new HashSet<>();
    private Set<Integer> enemyTargets = new HashSet<>(Arrays.asList(1, 2));

    @Override
    public BattlefieldHolder cast(BattlefieldHolder battlefieldHolder,
								  String senderId,
								  Integer victimPosition,
								  Boolean forEnemy) {
    	battlefieldHolder.getBattleHeroes(senderId, forEnemy).get(victimPosition).hit(10);
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
