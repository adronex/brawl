package by.brawl.ws.dto;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ClientRequestDto {
    private ClientRequestType type;
    @JsonIgnore
    private String message;
}
