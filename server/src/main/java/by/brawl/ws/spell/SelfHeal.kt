package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder
import java.util.Objects

class SelfHeal : SpellLogic {
    override val id = "2"
    override val targetable = false
    override val myTargets = HashSet<Int>()
    override val enemyTargets = HashSet<Int>()

    override fun cast(battlefieldHolder: BattlefieldHolder,
                      senderId: String,
                      victimPosition: Int?,
                      forEnemy: Boolean?): BattlefieldHolder {
        val heroes = battlefieldHolder.getBattleHeroes(senderId, false)

        for (hero in heroes) {
            if (senderId == hero.id) {
                hero.heal(8)
            }
        }
        return battlefieldHolder
    }
}
