package by.brawl.ws.huihui.conf

class SingleImpactConfig(private val value: Int): ImpactConfig<Int> {

    override fun getValue(): Int = value
}