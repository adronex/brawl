package by.brawl.ws.huihui.conf

import by.brawl.ws.holder.RoomHolder
import by.brawl.ws.holder.HeroHolder

class SpellConfig(id: String,
                  casterPositions: List<Int>?,
                  targetPositions: List<Int>?,
                  casterIntegerImpacts: List<IntegerImpactConfig>?,
                  targetIntegerImpacts: List<IntegerImpactConfig>?,
                  additionalIntegerImpacts: Map<Int, List<IntegerImpactConfig>>?,
                  logic: ((roomHolder: RoomHolder, caster: HeroHolder) -> RoomHolder)?,
                  suspend: Int?,
                  cooldown: Int?,
                  charges: Int?,
                  channeling: Boolean? = null,
                  channelDuration: Int? = null,
                  val targetLinks: Map<Int, Int> = mapOf(),
                  val unlockable: Boolean = false,
                  val unlockLogic: (() -> Boolean) = { true },
                  val lockable: Boolean = false,
                  val lockLogic: (() -> Boolean) = { true }) {

    val id: String = id
    val casterPositions: List<Int> = casterPositions ?: listOf()
    val targetPositions: List<Int> = targetPositions ?: listOf()
    val casterIntegerImpacts: List<IntegerImpactConfig> = casterIntegerImpacts ?: listOf()
    val targetIntegerImpacts: List<IntegerImpactConfig> = targetIntegerImpacts ?: listOf()
    // todo: additional own/enemies impacts
   // val additionalIntegerImpacts: Map<Int, List<IntegerImpactConfig>> = additionalIntegerImpacts ?: mapOf()
    val logic: (roomHolder: RoomHolder, caster: HeroHolder) -> RoomHolder = logic ?: { holder, _ -> holder }
    val suspend: Int = suspend ?: 0
    val cooldown: Int = cooldown ?: 0
    val chargeable: Boolean = charges != null
    val charges: Int = charges ?: 0
    val channeling: Boolean = channeling ?: false
    val channelDuration: Int = channelDuration ?: 0
}