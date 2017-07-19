package by.brawl.ws.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.codehaus.jackson.annotate.JsonIgnore

@JsonInclude(JsonInclude.Include.NON_NULL)
open class AbstractDto : JsonDto {

    @JsonIgnore
    @Throws(JsonProcessingException::class)
    override fun asJson(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}
