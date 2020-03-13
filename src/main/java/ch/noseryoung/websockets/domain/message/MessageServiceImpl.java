package ch.noseryoung.websockets.domain.message;

import ch.noseryoung.websockets.core.generic.AbstractEntityServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends AbstractEntityServiceImpl<Message> implements MessageService {

    @Autowired
    public MessageServiceImpl(MessageRepository repository, Logger logger) {
        super(repository, logger);
    }
}
