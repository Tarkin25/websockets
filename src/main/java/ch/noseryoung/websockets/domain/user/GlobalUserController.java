package ch.noseryoung.websockets.domain.user;

import ch.noseryoung.websockets.domain.chat.ChatUserService;
import ch.noseryoung.websockets.domain.user.dto.UserDTO;
import ch.noseryoung.websockets.domain.user.dto.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class GlobalUserController {

    private UserService userService;
    private UserMapper userMapper;
    private ChatUserService chatUserService;

    @Autowired
    public GlobalUserController(UserService userService, UserMapper userMapper, ChatUserService chatUserService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.chatUserService = chatUserService;
    }

    @GetMapping
    public ResponseEntity<Collection<UserDTO>> findAll(
            @RequestParam(required = false) String excludeChatId
    ) {
        Collection<User> users;

        if(excludeChatId != null) {
            users = chatUserService.findUsersNotIn(excludeChatId);
        } else {
            users = userService.findAll();
        }

        return new ResponseEntity<>(userMapper.toDTOs(users), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO.WithPassword userDTO) {
        User user = userService.create(userMapper.fromDTO(userDTO));

        return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById(id);

        return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
    }

}
