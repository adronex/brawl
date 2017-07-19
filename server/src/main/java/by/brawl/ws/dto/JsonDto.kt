package by.brawl.ws.dto

import com.fasterxml.jackson.core.JsonProcessingException

interface JsonDto {

    @Throws(JsonProcessingException::class)
    fun asJson(): String
}
