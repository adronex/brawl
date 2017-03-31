package by.brawl.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.annotate.JsonIgnore;

public class MessageDto {

    private String message;

    public MessageDto(String message) {
        this.message = message;
    }

    @JsonIgnore
    public String asJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "hui";
    }

    public String getMessage() {
        return message;
    }
}
