package by.brawl.controller

import by.brawl.dto.SpellStaticDataDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/static")
class StaticDataController {

    val lola = mapOf(Pair(1, 2), Pair(3, 4))

    private val spellData = mapOf(
            Pair("1", SpellStaticDataDto("1", "Sucker Punch", true, setOf(), setOf(1, 2))),
            Pair("2", SpellStaticDataDto("2", "Self Heal", false, setOf(), setOf())),
            Pair("3", SpellStaticDataDto("3", "Damaged Heal", true, setOf(1, 2, 3, 4), setOf())),
            Pair("4", SpellStaticDataDto("4", "Divine Comfort", false, setOf(), setOf())),
            Pair("5", SpellStaticDataDto("5", "Eldrich Pull", true, setOf(), setOf(4))),
            Pair("6", SpellStaticDataDto("6", "Uppercut", true, setOf(), setOf(1))),
            Pair("7", SpellStaticDataDto("7", "Shoot Them All", false, setOf(), setOf())),
            Pair("8", SpellStaticDataDto("8", "Judgement", true, setOf(), setOf(1, 2, 3)))
    )

    @GetMapping(path = arrayOf("/spells"))
    fun getStaticData() = spellData
}