package by.brawl.controller

import by.brawl.service.HeroService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/heroes")
class HeroController constructor(val service: HeroService) {

    @GetMapping(path = arrayOf("/my"))
    fun findMySquads() = service.findMy()

}