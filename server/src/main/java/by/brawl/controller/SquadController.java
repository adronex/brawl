package by.brawl.controller;

import by.brawl.dto.SquadToChooseDto;
import by.brawl.entity.IdEntity;
import by.brawl.service.SquadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/squads")
public class SquadController {

    @Autowired
    private SquadService service;

    @GetMapping(path = "/my")
    public Set<SquadToChooseDto> getMySquads(){
        return service.getMySquads().stream().map(SquadToChooseDto::new).collect(Collectors.toSet());
    }
}
