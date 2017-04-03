package by.brawl.service;

import by.brawl.entity.Hero;
import by.brawl.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroServiceImpl implements HeroService {

    @Autowired
    private HeroRepository repository;

    @Override
    public Hero findOne(String id) {
        return repository.findOne(id);
    }
}
