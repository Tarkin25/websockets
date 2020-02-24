package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.chat.dto.ChatDTO;
import ch.noseryoung.websockets.domain.chat.dto.ChatMapper;
import ch.noseryoung.websockets.domain.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/chats")
public class ChatController {

    private ChatService chatService;
    private ChatMapper chatMapper;

    @Autowired
    public ChatController(ChatService chatService, ChatMapper chatMapper) {
        this.chatService = chatService;
        this.chatMapper = chatMapper;
    }

    @PostMapping
    public ResponseEntity<ChatDTO> create(@RequestBody @Valid ChatDTO chatDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Chat chat = chatService.create(chatMapper.fromDTO(chatDTO), userDetails.getUser());

        return new ResponseEntity<>(chatMapper.toDTO(chat), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatDTO> updateById(@PathVariable String id, @RequestBody @Valid ChatDTO chatDTO) {
        Chat chat = chatService.updateById(id, chatMapper.fromDTO(chatDTO));

        return new ResponseEntity<>(chatMapper.toDTO(chat), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        chatService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
