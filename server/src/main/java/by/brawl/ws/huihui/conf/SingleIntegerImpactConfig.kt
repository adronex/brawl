package by.brawl.ws.huihui.conf

class SingleIntegerImpactConfig(override val type: ImpactType,
                                value: Int): IntegerImpactConfig {
    override val from = value
    override val to = value
}