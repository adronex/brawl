package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder

import java.util.Arrays
import java.util.HashSet

class SuckerPunch : SpellLogic {
    override val id = "1"
    override val targetable = true
    override val myTargets = HashSet<Int>()
    override val enemyTargets = hashSetOf(1, 2)

    override fun cast(battlefieldHolder: BattlefieldHolder,
                      senderId: String,
                      victimPosition: Int?,
                      forEnemy: Boolean?): BattlefieldHolder {
        battlefieldHolder.getBattleHeroes(senderId, forEnemy)[victimPosition!!].hit(10)
        return battlefieldHolder
    }
}
