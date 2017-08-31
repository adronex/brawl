package by.brawl.ws.spell

import by.brawl.ws.holder.BattlefieldHolder

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