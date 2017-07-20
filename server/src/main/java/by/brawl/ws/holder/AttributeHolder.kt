package by.brawl.ws.holder

class AttributeHolder {

    var max = 0
    var current = 0

    constructor(bothValues: Int) {
        max = bothValues
        current = bothValues
    }

    constructor(max: Int, current: Int) {
        this.max = max
        this.current = current
    }

    fun incrementCurrent(value: Int) {
        current += value
        if (current > max) {
            current = max
        }
    }

    fun decrementCurrent(value: Int) {
        current -= value
        if (current < 0) {
            current = 0
        }
    }

}