package by.brawl.ws.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonIgnore;

public interface JsonDto {
    @JsonIgnore
    String asJson() throws JsonProcessingException;
}
