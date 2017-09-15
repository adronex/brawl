package by.brawl.ws.huihui.impact

interface Impact<E> {
    fun getType(): ImpactType
    fun getValue(): E
}