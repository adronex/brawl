package by.brawl.ws.huihui.conf

import java.util.*

class RangeIntegerImpactConfig(private val type: ImpactType,
                               private val from: Int,
                               private val to: Int): IntegerImpactConfig {

    // todo: random utils
    private val random = Random()

    override fun getType(): ImpactType = type

    override fun calculateValue() = random.nextInt(to - from + 1) + from
}