package by.brawl.ws.service

import by.brawl.util.Exceptions
import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.StepLogHolder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Deprecated("Use huihui package")
internal class SpellCastServiceImpl (val spells:Spells): SpellCastService {

    // todo: check for suspend/cooldown
    // todo: check for valid target
    override fun castSpell(spellId: String,
                           senderId: String,
                           casterId: String,
                           victimPosition: Int?,
                           forEnemy: Boolean?,
                           battlefieldHolder: BattlefieldHolder): BattlefieldHolder {

        val castedSpell = spells.spellsMap[spellId] ?: throw Exceptions.produceNullPointer(LOG, "Casted spell with id $spellId is absent in spell pool")
        // check target for validity
        val cannotBeTargeted = victimPosition == null && !castedSpell.targetable
        val validMyTarget = victimPosition != null && forEnemy != null && !forEnemy && castedSpell.myTargets.contains(victimPosition)
        val validEnemyTarget = victimPosition != null && forEnemy != null && forEnemy && castedSpell.enemyTargets.contains(victimPosition)
        if (!cannotBeTargeted && !validMyTarget && !validEnemyTarget) {
            throw Exceptions.produceIllegalArgument(LOG, "Spell targeting error. My available targets:${castedSpell.myTargets}, enemy available targets: ${castedSpell.enemyTargets}, victim position: $victimPosition, for enemy: $forEnemy.")
        }
        val response = castedSpell.cast(battlefieldHolder, senderId, victimPosition, forEnemy)
        val stepLog = StepLogHolder(
                senderId,
                spellId,
                casterId
        )
        response.battleLog.add(stepLog)
        response.moveQueueWithDeadRemoval()
        return response
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(SpellCastServiceImpl::class.java)
    }

}