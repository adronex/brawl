package by.brawl.ws.huihui.impact

class IntegerImpact(val impactType: ImpactType, val value: Int): Impact<Int> {

    override fun getType(): ImpactType {
        return impactType;
    }

    override fun getValue(): Int {
        return value;
    }
}