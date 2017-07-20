package by.brawl.controller

import by.brawl.dto.PreGameSquadDto
import by.brawl.service.SquadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/squads")
class SquadController constructor(val service: SquadService) {

    @GetMapping(path = arrayOf("/my"))
    fun findMySquads() = service.getMySquads().map { PreGameSquadDto(it) }.toSet()

}