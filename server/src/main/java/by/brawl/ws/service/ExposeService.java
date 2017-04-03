package by.brawl.ws.service;

import by.brawl.entity.Account;
import by.brawl.ws.dto.HeroDto;
import org.springframework.data.util.Pair;

import java.util.Set;

public interface ExposeService {

    Pair<Set<HeroDto>, Set<HeroDto>> getExposedMulliganDto(Account receiver);
}
