package by.brawl.ws.dto;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonDto {

    String asJson() throws JsonProcessingException;
}
