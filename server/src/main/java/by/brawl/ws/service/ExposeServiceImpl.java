package by.brawl.ws.service;

import by.brawl.entity.Account;
import by.brawl.ws.dto.HeroDto;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class ExposeServiceImpl implements ExposeService {

    @Override
    public Pair<Set<HeroDto>, Set<HeroDto>> getExposedMulliganDto(Account receiver) {
        return null;
    }
}
