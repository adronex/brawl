package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder

class ShootThemAll : SpellLogic {

    override val id = "7"
    override val targetable = false
    override val myTargets = HashSet<Int>()
    override val enemyTargets = hashSetOf(1, 2, 3, 4)

    override fun cast(battlefieldHolder: BattlefieldHolder,
                      senderId: String,
                      victimPosition: Int?,
                      forEnemy: Boolean?): BattlefieldHolder {
        val heroes = battlefieldHolder.getBattleHeroes(senderId, true)

        for (hero in heroes) {
            hero.hit(4)
        }
        return battlefieldHolder
    }
}