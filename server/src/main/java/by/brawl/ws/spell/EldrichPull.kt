package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder

import java.util.Arrays
import java.util.HashSet

class EldrichPull : SpellLogic {
    override val id = "5"
    override val targetable = true
    override val myTargets = HashSet<Int>()
    override val enemyTargets = hashSetOf(4)

    override fun cast(battlefieldHolder: BattlefieldHolder,
                      senderId: String,
                      victimPosition: Int?,
                      forEnemy: Boolean?): BattlefieldHolder {
        val heroes = battlefieldHolder.getBattleHeroes(senderId, true)
        val movingHero = heroes[0]
        heroes.add(movingHero)
        heroes.removeAt(0)
        return battlefieldHolder
    }
}
