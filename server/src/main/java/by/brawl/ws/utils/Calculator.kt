package by.brawl.ws.utils

import by.brawl.ws.huihui.conf.IntegerImpactConfig
import java.util.*

class Calculator {

    companion object {

        private val random = Random()

        fun calculate(integerImpactConfig: IntegerImpactConfig): Int {
            return random.nextInt(integerImpactConfig.to - integerImpactConfig.from + 1) + integerImpactConfig.from
        }
    }
}