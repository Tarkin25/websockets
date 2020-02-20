package ch.noseryoung.websockets.domain.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MessageController {

    private MessageService messageService;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/messages")
    @ResponseBody
    public ResponseEntity<List<Message>> findAll() {
        return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Message> create(@RequestBody Message message) {
        message = messageService.create(message);

        messagingTemplate.convertAndSend("/topic/messages", message);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @MessageMapping("/create-message")
    @SendTo("/topic/messages")
    public Message message(Message message) {
        return messageService.create(message);
    }
}
