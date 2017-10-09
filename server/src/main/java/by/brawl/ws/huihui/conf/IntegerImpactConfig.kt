package by.brawl.ws.huihui.conf

interface IntegerImpactConfig {
    fun getType(): ImpactType
    fun calculateValue(): Int
}