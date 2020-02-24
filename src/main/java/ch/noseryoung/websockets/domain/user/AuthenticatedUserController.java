package ch.noseryoung.websockets.domain.user;

import ch.noseryoung.websockets.domain.chat.Chat;
import ch.noseryoung.websockets.domain.chat.dto.ChatDTO;
import ch.noseryoung.websockets.domain.chat.dto.ChatMapper;
import ch.noseryoung.websockets.domain.user.dto.UserDTO;
import ch.noseryoung.websockets.domain.user.dto.UserMapper;
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

    private UserMapper userMapper;
    private ChatMapper chatMapper;

    @Autowired
    public AuthenticatedUserController(UserMapper userMapper, ChatMapper chatMapper) {
        this.userMapper = userMapper;
        this.chatMapper = chatMapper;
    }

    @GetMapping
    public ResponseEntity<UserDTO> findAuthenticated(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(userMapper.toDTO(userDetails.getUser()), HttpStatus.OK);
    }

    @GetMapping("/chats")
    public ResponseEntity<Collection<ChatDTO>> getChats(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Collection<Chat> chats = userDetails.getUser().getChats();

        return new ResponseEntity<>(chatMapper.toDTOs(chats), HttpStatus.OK);
    }

}
