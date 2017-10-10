package by.brawl.ws.huihui.conf

import by.brawl.ws.holder.BattlefieldHolder
import by.brawl.ws.holder.HeroHolder

class SpellConfig(val id: String,
                  val casterPositions: List<Int> = listOf(),
                  val targetPositions: List<Int> = listOf(),
                  val casterIntegerImpacts: List<IntegerImpactConfig> = listOf(),
                  val targetIntegerImpacts: List<IntegerImpactConfig> = listOf(),
                  val additionalIntegerImpacts: Map<Int, List<IntegerImpactConfig>> = mapOf(),
                  val logic: (battlefieldHolder: BattlefieldHolder, caster: HeroHolder) -> BattlefieldHolder = { holder, _ -> holder },
                  val suspend: Int = 0,
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