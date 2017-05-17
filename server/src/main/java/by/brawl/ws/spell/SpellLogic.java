package by.brawl.ws.spell;

import by.brawl.ws.holder.BattlefieldHolder;

import java.util.Set;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 14.04.2017.
 */
public interface SpellLogic {

    BattlefieldHolder cast(BattlefieldHolder battlefieldHolder,
                           String senderId,
                           Integer victimPosition,
                           Boolean forEnemy);
    String getId();
    Boolean getTargetable();
    Set<Integer> getMyTargets();
    Set<Integer> getEnemyTargets();
}
