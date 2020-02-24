package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.user.User;
import ch.noseryoung.websockets.domain.user.dto.UserDTO;
import ch.noseryoung.websockets.domain.user.dto.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/chats/{chatId}/users")
public class ChatUserController {

    private ChatUserService chatUserService;
    private UserMapper userMapper;

    @Autowired
    public ChatUserController(ChatUserService chatUserService, UserMapper userMapper) {
        this.chatUserService = chatUserService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<UserDTO>> findAll(@PathVariable String chatId) {
        Collection<User> users = chatUserService.findUsers(chatId);

        return new ResponseEntity<>(userMapper.toDTOs(users), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addToChat(@PathVariable String chatId, @RequestParam String userId) {
        User user = chatUserService.addToChat(chatId, userId);

        return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeFromChat(@PathVariable String chatId, @PathVariable String userId) {
        chatUserService.removeFromChat(chatId, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
