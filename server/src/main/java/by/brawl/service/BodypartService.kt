package by.brawl.service

import by.brawl.entity.bodypart.Bodypart

interface BodypartService {
    fun findAll(): Map<String, Set<Bodypart>>
}