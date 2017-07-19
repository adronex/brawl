package by.brawl.ws.service

import by.brawl.util.Exceptions
import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.StepLogHolder
import by.brawl.ws.spell.SpellLogic
import org.reflections.Reflections
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import java.util.HashMap

@Service
internal class SpellCastServiceImpl : SpellCastService {

    private val spellsMap = HashMap<String, SpellLogic>()

    @PostConstruct
    @Throws(InstantiationException::class, IllegalAccessException::class)
    fun init() {

        findAllSpellsLogic(SpellLogic::class.java.`package`.name)
    }

    @Throws(IllegalAccessException::class, InstantiationException::class)
    private fun findAllSpellsLogic(scanPackage: String) {

        val reflections = Reflections(scanPackage)

        val allClasses = reflections.getSubTypesOf(SpellLogic::class.java)
        for (spellLogicClass in allClasses) {
            val spellLogic = spellLogicClass.newInstance()
            spellsMap.put(spellLogic.id, spellLogic)
        }
    }

    // todo: check for suspend/cooldown
    // todo: check for valid target
    override fun castSpell(spellId: String,
                           senderId: String,
                           casterId: String,
                           victimPosition: Int?,
                           forEnemy: Boolean?,
                           battlefieldHolder: BattlefieldHolder): BattlefieldHolder {

        val castedSpell = spellsMap[spellId] ?: throw Exceptions.produceNullPointer(LOG, "Casted spell with id {0} is absent in spells pool", spellId)
        // check target for validity
        val cannotBeTargeted = victimPosition == null && !castedSpell.targetable
        val validMyTarget = victimPosition != null && forEnemy != null && !forEnemy && castedSpell.myTargets.contains(victimPosition)
        val validEnemyTarget = victimPosition != null && forEnemy != null && forEnemy && castedSpell.enemyTargets.contains(victimPosition)
        if (!cannotBeTargeted && !validMyTarget && !validEnemyTarget) {
            throw Exceptions.produceIllegalArgument(LOG, "Spell targeting error. My available targets:{0}, enemy available targets: {1}, victim position: {2}, for enemy: {3}.",
                    castedSpell.myTargets, castedSpell.enemyTargets, victimPosition!!, forEnemy!!)
        }
        val response = castedSpell.cast(battlefieldHolder, senderId, victimPosition, forEnemy)
        val stepLog = StepLogHolder(
                senderId,
                spellId,
                casterId
        )
        //todo: add function ?!
        //response.battleLog.add(stepLog)
        response.moveQueueWithDeadRemoval()
        return response
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(SpellCastServiceImpl::class.java)
    }
}
