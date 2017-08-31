package by.brawl.ws.huihui

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder

class SpellConfig(val id: String,
                  val casterPositions: List<Int> = listOf(),
                  val targetPositions: List<Int> = listOf(),
                  val casterImpacts: Map<ImpactType, Any> = mapOf(),
                  val targetImpacts: Map<ImpactType, Any> = mapOf(),
                  val logic: (caster: HeroHolder) -> BattlefieldHolder,
                  val delay: Int = 0,
                  val cooldown: Int = 0,
                  val channeling: Boolean = false,
                  val channelDuration: Int = 0,
                  val chargeable: Boolean = false,
                  val charges: Int = 0,
                  val targetLinks: Map<Int, Int> = mapOf(),
                  val unlockable: Boolean = false,
                  val unlockLogic: () -> Boolean,
                  val lockable: Boolean = false,
                  val lockLogic: () -> Boolean) {

}