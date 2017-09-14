package by.brawl.ws.huihui.conf

import java.util.*

class RangeImpactConfig(private val from: Int,
                        private val to: Int): ImpactConfig<Int> {
    private val random = Random()

    override fun getValue() = random.nextInt(to - from + 1) + from
}