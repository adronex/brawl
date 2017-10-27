package by.brawl.controller

import by.brawl.dto.HeroDto
import by.brawl.dto.IdDto
import by.brawl.dto.SubmitHeroDto
import by.brawl.service.HeroService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/heroes")
class HeroController (service: HeroService): GenericController<HeroService>(service) {

    @GetMapping(path = arrayOf("/my"))
    fun findMySquads() = service.findMy()

    @PutMapping(path = arrayOf("/submit"))
    fun submit(@RequestBody dto: SubmitHeroDto): HeroDto = service.submit(dto)

    @DeleteMapping(path = arrayOf("/delete/{id}"))
    fun delete(@PathVariable id: String): IdDto = service.delete(id)

}