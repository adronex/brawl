package by.brawl.ws.spell;

import by.brawl.ws.holder.HeroHolder;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Set;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 14.04.2017.
 */
public interface SpellLogic {

    Pair<List<HeroHolder>, List<HeroHolder>> cast(List<HeroHolder> myHeroes,
                                                  List<HeroHolder> enemyHeroes,
                                                  Integer target);
    String getId();
    Boolean getTargetable();
    Set<Integer> getMyTargets();
    Set<Integer> getEnemyTargets();
}
