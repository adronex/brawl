package by.brawl.ws.huihui.conf

class SingleIntegerImpactConfig(private val type: ImpactType,
                                private val value: Int): IntegerImpactConfig {

    override fun getType(): ImpactType = type

    override fun calculateValue(): Int = value
}