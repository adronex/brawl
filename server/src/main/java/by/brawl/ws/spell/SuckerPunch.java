package by.brawl.ws.spell;

import by.brawl.ws.holder.HeroHolder;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SuckerPunch implements SpellLogic {
    private String id = "1";
    private Boolean targetable = true;
    private Set<Integer> myTargets = new HashSet<>();
    private Set<Integer> enemyTargets = new HashSet<>(Arrays.asList(new Integer[]{1}));

    @Override
    public Pair<List<HeroHolder>, List<HeroHolder>> cast(List<HeroHolder> myHeroes,
                                                         List<HeroHolder> enemyHeroes,
                                                         Integer target) {
        enemyHeroes.get(target).hit(15);
        return Pair.of(myHeroes, enemyHeroes);
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
