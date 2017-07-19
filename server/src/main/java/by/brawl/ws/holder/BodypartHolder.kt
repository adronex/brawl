package by.brawl.ws.holder

import by.brawl.entity.bodypart.Bodypart
import by.brawl.entity.bodypart.BodypartType

class BodypartHolder(bodypart: Bodypart) {
    val id = bodypart.id
    val type = bodypart.type
}
