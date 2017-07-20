package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder

/**
 * Default class description.

 * @author P.Sinitsky.
 * *         Created on 14.04.2017.
 */
interface SpellLogic {

    fun cast(battlefieldHolder: BattlefieldHolder,
             senderId: String,
             victimPosition: Int?,
             forEnemy: Boolean?): BattlefieldHolder

    val id: String
    val targetable: Boolean
    val myTargets: Set<Int>
    val enemyTargets: Set<Int>
}