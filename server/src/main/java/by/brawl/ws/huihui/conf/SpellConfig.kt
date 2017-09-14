package by.brawl.ws.huihui.conf

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder
import by.brawl.ws.huihui.impact.ImpactType

class SpellConfig(val id: String,
                  val casterPositions: List<Int> = listOf(),
                  val targetPositions: List<Int> = listOf(),
                  val casterImpacts: Map<ImpactType, Any> = mapOf(),
                  val targetImpacts: Map<ImpactType, Any> = mapOf(),
                  val logic: (battlefieldHolder: BattlefieldHolder, caster: HeroHolder) -> BattlefieldHolder,
                  val delay: Int = 0,
                  val cooldown: Int = 0,
                  val channeling: Boolean = false,
                  val channelDuration: Int = 0,
                  val chargeable: Boolean = false,
                  val charges: Int = 0,
                  val targetLinks: Map<Int, Int> = mapOf(),
                  val unlockable: Boolean = false,
                  val unlockLogic: (() -> Boolean)? = null,
                  val lockable: Boolean = false,
                  val lockLogic: (() -> Boolean)? = null) {

}