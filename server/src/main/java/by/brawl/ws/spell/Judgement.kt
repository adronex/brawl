package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder

import java.util.Arrays
import java.util.HashSet
import java.util.Objects

class Judgement : SpellLogic {
    override val id = "8"
    override val targetable = true
    override val myTargets = HashSet<Int>()
    override val enemyTargets = hashSetOf(1, 2, 3)

    override fun cast(battlefieldHolder: BattlefieldHolder,
                      senderId: String,
                      victimPosition: Int?,
                      forEnemy: Boolean?): BattlefieldHolder {
        battlefieldHolder.getBattleHeroes(senderId, forEnemy)[victimPosition!!].hit(7)
        val heroes = battlefieldHolder.getBattleHeroes(senderId, false)

        for (hero in heroes) {
            if (senderId == hero.id) {
                hero.heal(4)
            }
        }
        return battlefieldHolder
    }
}
