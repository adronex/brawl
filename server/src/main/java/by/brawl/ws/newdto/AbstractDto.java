package by.brawl.ws.newdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.annotate.JsonIgnore;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AbstractDto implements JsonDto {

    @JsonIgnore
    @Override
    public String asJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
