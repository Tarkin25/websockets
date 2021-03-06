package ch.noseryoung.websockets.domain.user;

import ch.noseryoung.websockets.domain.chat.Chat;
import ch.noseryoung.websockets.domain.chat.ChatService;
import ch.noseryoung.websockets.domain.chat.dto.ChatDTO;
import ch.noseryoung.websockets.domain.chat.dto.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class AuthenticatedUserController {

    private ChatMapper chatMapper;
    private ChatService chatService;

    @Autowired
    public AuthenticatedUserController(ChatMapper chatMapper, ChatService chatService) {
        this.chatMapper = chatMapper;
        this.chatService = chatService;
    }

    @GetMapping("/chats")
    public ResponseEntity<Collection<ChatDTO>> getChats(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Collection<Chat> chats = chatService.findAllByUser(userDetails.getUser());

        return new ResponseEntity<>(chatMapper.toDTOs(chats), HttpStatus.OK);
    }

}
