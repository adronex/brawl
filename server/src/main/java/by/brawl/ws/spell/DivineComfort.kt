package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder
import java.util.*

class DivineComfort : SpellLogic {

    override val id = "4"
    override val targetable = false
    override val myTargets = HashSet<Int>()
    override val enemyTargets = HashSet<Int>()

    override fun cast(battlefieldHolder: BattlefieldHolder,
                      senderId: String,
                      victimPosition: Int?,
                      forEnemy: Boolean?): BattlefieldHolder {
        val heroes = battlefieldHolder.getBattleHeroes(senderId, false)

        for (hero in heroes) {
            hero.heal(3)
        }
        return battlefieldHolder
    }

}