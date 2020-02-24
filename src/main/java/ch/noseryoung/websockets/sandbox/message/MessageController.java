package ch.noseryoung.websockets.sandbox.message;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MessageController {

    private Logger logger;

    @Autowired
    public MessageController(Logger logger) {
        this.logger = logger;
    }

    @MessageMapping("/secured/chat/{id}")
    @SendTo("/secured/history/{id}")
    public OutputMessage send(Message message, Principal principal, @DestinationVariable String id) {
        logger.debug("got message");
        logger.debug("id: {}", id);

        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
        return new OutputMessage(principal.getName(), message.getContent(), time);
    }

}
