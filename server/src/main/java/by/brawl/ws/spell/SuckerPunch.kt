package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder
import java.util.*

class SuckerPunch : SpellLogic {

    override val id = "1"
    override val targetable = true
    override val myTargets = HashSet<Int>()
    override val enemyTargets = hashSetOf(1, 2)

    override fun cast(battlefieldHolder: BattlefieldHolder,
                      senderId: String,
                      victimPosition: Int?,
                      forEnemy: Boolean?): BattlefieldHolder {
        // TODO: REWORK API!!! REMOVE ALL NULLABLE VARIABLES
        battlefieldHolder.getBattleHeroes(senderId, forEnemy!!)[victimPosition!!].hit(10)
        return battlefieldHolder
    }
}