package ch.noseryoung.websockets.domain.message;

import java.util.List;

public interface MessageService {

    List<Message> findAll();

    Message create(Message message);

}
