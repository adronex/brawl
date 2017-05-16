package by.brawl.ws.service;

import by.brawl.util.Exceptions;
import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.StepLogHolder;
import by.brawl.ws.spell.SpellLogic;
import by.brawl.ws.spell.SuckerPunch;
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
        SpellLogic suckerPunch = new SuckerPunch();
        spellsMap.put(suckerPunch.getId(), suckerPunch);
	}

    // todo: split senders to senders/casters and receivers to receivers/victims
    @Override
    public BattlefieldHolder castSpell(String id,
                                       String senderAccountId,
                                       String senderId,
                                       Integer receiverPositionId,
                                       Boolean enemy,
                                       BattlefieldHolder battlefieldHolder) {
        SpellLogic castedSpell = spellsMap.get(id);
        if (castedSpell == null) {
            throw Exceptions.produceNullPointer(LOG, "Casted spell is absent in spells pool");
        }
        // check target for validity
        Boolean cannotBeTargeted = (receiverPositionId == null && !castedSpell.getTargetable());
        Boolean validMyTarget = receiverPositionId != null && enemy != null && !enemy && castedSpell.getMyTargets().contains(receiverPositionId);
        Boolean validEnemyTarget = receiverPositionId != null && enemy != null && enemy && castedSpell.getEnemyTargets().contains(receiverPositionId);
        if (!cannotBeTargeted && !validMyTarget && !validEnemyTarget) {
            throw Exceptions.produceIllegalArgument(LOG, "Targeting error. Can't be targeted: {0}, valid my target: {1}, valid enemy target: {2}",
                    cannotBeTargeted, validEnemyTarget, validEnemyTarget);
        }
        // todo: cast spells right here
        StepLogHolder stepLog = new StepLogHolder(
                senderAccountId,
                id,
                senderId
        );
        battlefieldHolder.getBattleLog().add(stepLog);
        battlefieldHolder.moveQueueWithDeadRemoval();
        return battlefieldHolder;
    }
}
