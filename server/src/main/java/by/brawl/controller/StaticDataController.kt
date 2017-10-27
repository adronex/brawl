package by.brawl.controller

import by.brawl.service.BodypartService
import by.brawl.ws.huihui.SpellsPool
import by.brawl.ws.huihui.conf.SpellConfig
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/static")
class StaticDataController(service: BodypartService): GenericController<BodypartService>(service) {

    @GetMapping(path = arrayOf("/spells"))
    fun getSpellData(): Map<String, SpellConfig> = SpellsPool.spellsMap

    @GetMapping(path = arrayOf("/bodyparts"))
    fun getBodypartsData() = service.findAll()
}