package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.impact.Impact
import java.util.*

class Uppercut {

//    override val id = "6"
//    override val targetable = true
//    override val myTargets = HashSet<Int>()
//    override val enemyTargets = hashSetOf(1)

    companion object {
        fun cast(battlefieldHolder: BattlefieldHolder,
                 senderId: String,
                 victimPosition: Int?,
                 forEnemy: Boolean?): BattlefieldHolder {

            val sourceList = battlefieldHolder.getBattleHeroes(senderId, true).toMutableList()
            val modifiedHeroesList = LinkedList(sourceList)
            val movingHero = modifiedHeroesList[victimPosition!! - 1]
            modifiedHeroesList.addFirst(movingHero)
            modifiedHeroesList.removeLast()
            sourceList.clear()
            sourceList.addAll(modifiedHeroesList)
            return battlefieldHolder
        }

        fun newCast(battlefieldHolder: BattlefieldHolder, impacts: Map<HeroHolder, List<Impact>>): BattlefieldHolder {

        }
    }
}