package by.brawl.ws.dto;

public class MessageDto extends AbstractDto {

    private String message;

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
