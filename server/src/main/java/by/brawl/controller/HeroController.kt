package by.brawl.controller

import by.brawl.dto.HeroDto
import by.brawl.service.HeroService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/heroes")
class HeroController constructor(val service: HeroService) {

    @GetMapping(path = arrayOf("/my"))
    fun findMySquads() = service.findMy()

    @PostMapping(path = arrayOf("/submit"))
    fun submit(@RequestBody heroDto: HeroDto) = service.findMy()

}