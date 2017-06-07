package by.brawl.ws.service;

import by.brawl.util.Exceptions;
import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.StepLogHolder;
import by.brawl.ws.spell.DamagedHeal;
import by.brawl.ws.spell.DivineComfort;
import by.brawl.ws.spell.EldrichPull;
import by.brawl.ws.spell.Judgement;
import by.brawl.ws.spell.SelfHeal;
import by.brawl.ws.spell.ShootThemAll;
import by.brawl.ws.spell.SpellLogic;
import by.brawl.ws.spell.SuckerPunch;
import by.brawl.ws.spell.Uppercut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
class SpellCastServiceImpl implements SpellCastService {

	private static final Logger LOG = LoggerFactory.getLogger(SpellCastServiceImpl.class);

	private Map<String, SpellLogic> spellsMap = new HashMap<>();

	@PostConstruct
	public void init() {
        SpellLogic damagedHeal = new DamagedHeal();
        SpellLogic divineComfort = new DivineComfort();
        SpellLogic eldrichPull = new EldrichPull();
        SpellLogic judgement = new Judgement();
        SpellLogic selfHeal = new SelfHeal();
        SpellLogic shootThemAll = new ShootThemAll();
        SpellLogic suckerPunch = new SuckerPunch();
        SpellLogic uppercut = new Uppercut();
        spellsMap.put(damagedHeal.getId(), damagedHeal);
        spellsMap.put(divineComfort.getId(), divineComfort);
        spellsMap.put(eldrichPull.getId(), eldrichPull);
        spellsMap.put(judgement.getId(), judgement);
        spellsMap.put(selfHeal.getId(), selfHeal);
        spellsMap.put(shootThemAll.getId(), shootThemAll);
        spellsMap.put(suckerPunch.getId(), suckerPunch);
        spellsMap.put(uppercut.getId(), uppercut);
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
            throw Exceptions.produceNullPointer(LOG, "Casted spellId ({0}) is absent in spells pool", spellId);
        }
        // check target for validity
        Boolean cannotBeTargeted = (victimPosition == null && !castedSpell.getTargetable());
        Boolean validMyTarget = victimPosition != null && forEnemy != null && !forEnemy && castedSpell.getMyTargets().contains(victimPosition);
        Boolean validEnemyTarget = victimPosition != null && forEnemy != null && forEnemy && castedSpell.getEnemyTargets().contains(victimPosition);
        if (!cannotBeTargeted && !validMyTarget && !validEnemyTarget) {
            throw Exceptions.produceIllegalArgument(LOG, "Targeting error. Can''t be targeted: {0}, valid my target: {1}, valid forEnemy target: {2}",
                    cannotBeTargeted, validEnemyTarget, validEnemyTarget);
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
