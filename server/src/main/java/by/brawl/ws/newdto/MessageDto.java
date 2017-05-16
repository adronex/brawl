package by.brawl.ws.newdto;

public class MessageDto extends AbstractDto {

    private String message;

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
