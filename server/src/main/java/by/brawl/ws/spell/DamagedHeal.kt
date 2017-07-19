package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder

import java.util.Arrays
import java.util.HashSet
import java.util.Objects

class DamagedHeal : SpellLogic {
    override val id = "3"
    override val targetable = true
    override val myTargets = hashSetOf(1, 2, 3, 4)
    override val enemyTargets = HashSet<Int>()

    override fun cast(battlefieldHolder: BattlefieldHolder,
                      senderId: String,
                      victimPosition: Int?,
                      forEnemy: Boolean?): BattlefieldHolder {
        battlefieldHolder.getBattleHeroes(senderId, false)[victimPosition!!].heal(12)
        val heroes = battlefieldHolder.getBattleHeroes(senderId, false)

        for (hero in heroes) {
            if (senderId == hero.id) {
                hero.hit(4)
            }
        }
        return battlefieldHolder
    }
}
