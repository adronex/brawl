package by.brawl.ws.service;

import by.brawl.util.Exceptions;
import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.StepLogHolder;
import by.brawl.ws.spell.SpellLogic;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
class SpellCastServiceImpl implements SpellCastService {

	private static final Logger LOG = LoggerFactory.getLogger(SpellCastServiceImpl.class);

	private Map<String, SpellLogic> spellsMap = new HashMap<>();

	@PostConstruct
	public void init() throws InstantiationException, IllegalAccessException {

        findAllSpellsLogic(SpellLogic.class.getPackage().getName());
	}

    private void findAllSpellsLogic(String scanPackage) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(scanPackage);

        Set<Class<? extends SpellLogic>> allClasses =
                reflections.getSubTypesOf(SpellLogic.class);
        for (Class<? extends SpellLogic> spellLogicClass : allClasses) {
            SpellLogic spellLogic = spellLogicClass.newInstance();
            spellsMap.put(spellLogic.getId(), spellLogic);
        }
    }

    // todo: check for suspend/cooldown
    // todo: check for valid target
    @Override
    public BattlefieldHolder castSpell(String spellId,
                                       String senderId,
                                       String casterId,
                                       Integer victimPosition,
                                       Boolean forEnemy,
                                       BattlefieldHolder battlefieldHolder) {
        SpellLogic castedSpell = spellsMap.get(spellId);
        if (castedSpell == null) {
            throw Exceptions.produceNullPointer(LOG, "Casted spell with id {0} is absent in spells pool", spellId);
        }
        // check target for validity
        Boolean cannotBeTargeted = (victimPosition == null && !castedSpell.getTargetable());
        Boolean validMyTarget = victimPosition != null && forEnemy != null && !forEnemy && castedSpell.getMyTargets().contains(victimPosition);
        Boolean validEnemyTarget = victimPosition != null && forEnemy != null && forEnemy && castedSpell.getEnemyTargets().contains(victimPosition);
        if (!cannotBeTargeted && !validMyTarget && !validEnemyTarget) {
            throw Exceptions.produceIllegalArgument(LOG, "Spell targeting error. My available targets:{0}, enemy available targets: {1}, victim position: {2}, for enemy: {3}.",
                    castedSpell.getMyTargets(), castedSpell.getEnemyTargets(), victimPosition, forEnemy);
        }
        battlefieldHolder = castedSpell.cast(battlefieldHolder, senderId, victimPosition, forEnemy);
        StepLogHolder stepLog = new StepLogHolder(
                senderId,
                spellId,
                casterId
        );
        battlefieldHolder.getBattleLog().add(stepLog);
        battlefieldHolder.moveQueueWithDeadRemoval();
        return battlefieldHolder;
    }
}
