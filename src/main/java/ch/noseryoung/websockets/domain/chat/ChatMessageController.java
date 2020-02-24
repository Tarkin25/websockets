package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.message.Message;
import ch.noseryoung.websockets.domain.message.dto.MessageDTO;
import ch.noseryoung.websockets.domain.message.dto.MessageMapper;
import ch.noseryoung.websockets.domain.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/chats/{chatId}/messages")
public class ChatMessageController {

    private ChatMessageService chatMessageService;
    private MessageMapper messageMapper;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService, MessageMapper messageMapper) {
        this.chatMessageService = chatMessageService;
        this.messageMapper = messageMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<MessageDTO>> findAll(@PathVariable String chatId) {
        Collection<Message> messages = chatMessageService.findMessages(chatId);

        return new ResponseEntity<>(messageMapper.toDTOs(messages), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageDTO> postToChat(@PathVariable String chatId, @RequestBody @Valid MessageDTO messageDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = chatMessageService.addMessage(chatId, messageMapper.fromDTO(messageDTO), userDetails.getUser());

        return new ResponseEntity<>(messageMapper.toDTO(message), HttpStatus.CREATED);
    }

}
