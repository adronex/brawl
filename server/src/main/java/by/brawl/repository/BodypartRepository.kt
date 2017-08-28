package by.brawl.repository

import by.brawl.entity.bodypart.Bodypart
import org.springframework.data.repository.CrudRepository

interface BodypartRepository: CrudRepository<Bodypart, String>