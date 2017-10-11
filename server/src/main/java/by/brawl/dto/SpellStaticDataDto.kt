package by.brawl.dto

import by.brawl.ws.huihui.conf.SpellConfig

@Deprecated("Use spellConfig directly maybe?")
class SpellStaticDataDto(config: SpellConfig) {
    val id = config.id
}