package by.brawl.controller

import by.brawl.dto.SubmitHeroDto
import by.brawl.service.HeroService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/heroes")
class HeroController constructor(val service: HeroService) {

    @GetMapping(path = arrayOf("/my"))
    fun findMySquads() = service.findMy()

    @PostMapping(path = arrayOf("/submit"))
    fun submit(@RequestBody dto: SubmitHeroDto) {
        service.submit(dto)
    }

}