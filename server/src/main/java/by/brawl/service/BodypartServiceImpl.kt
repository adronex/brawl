package by.brawl.service

import by.brawl.entity.bodypart.Bodypart
import by.brawl.entity.bodypart.BodypartType
import by.brawl.repository.BodypartRepository
import org.springframework.stereotype.Service

@Service
class BodypartServiceImpl
constructor(private val repository: BodypartRepository) : BodypartService {

    override fun findAll():Map<String, Set<Bodypart>> {
        val response = HashMap<BodypartType, MutableSet<Bodypart>>()
        val bodyparts = repository.findAll()
        bodyparts.forEach {
            val set = response.getOrDefault(it.type, mutableSetOf())
            set.add(it)
            response.put(it.type, set)
        }
        return response.mapKeys { it.key.name }
    }
}