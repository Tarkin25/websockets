package ch.noseryoung.websockets.domain.user;

import ch.noseryoung.websockets.core.generic.AbstractEntityService;
import ch.noseryoung.websockets.domain.chat.Chat;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface UserService extends AbstractEntityService<User>, UserDetailsService {

    Collection<User> findAllNotInChat(Chat chat);

}
