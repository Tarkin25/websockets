package ch.noseryoung.websockets.domain.message.dto;

import ch.noseryoung.websockets.domain.user.dto.UserDTO;
import ch.noseryoung.websockets.generic.AbstractDTO;

public class MessageDTO extends AbstractDTO {

    private String content;

    private UserDTO from;

    private String timestamp;

    public String getContent() {
        return content;
    }

    public MessageDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public UserDTO getFrom() {
        return from;
    }

    public MessageDTO setFrom(UserDTO from) {
        this.from = from;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public MessageDTO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
