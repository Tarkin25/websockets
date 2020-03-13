package ch.noseryoung.websockets.domain.chat.dto;

import ch.noseryoung.websockets.core.generic.AbstractDTO;

public class ChatDTO extends AbstractDTO {

    private String name;

    public String getName() {
        return name;
    }

    public ChatDTO setName(String name) {
        this.name = name;
        return this;
    }
}
