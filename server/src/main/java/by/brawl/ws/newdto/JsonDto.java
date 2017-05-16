package by.brawl.ws.newdto;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonDto {

    String asJson() throws JsonProcessingException;
}
