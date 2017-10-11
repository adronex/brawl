package by.brawl.ws.holder

import by.brawl.ws.huihui.conf.SpellConfig

class SpellHolder(val config: SpellConfig) {

    val id: String = config.id
    var suspend: Int = config.suspend
    var cooldown: Int = config.cooldown
    var charges: Int = config.charges

    fun onCast() {
        cooldown = config.cooldown
        if (charges > 0) charges--
    }

    fun onStartTurn() {
        if (suspend > 0) suspend--
        if (cooldown > 0) cooldown--
        if (charges > 0) charges--
    }
}