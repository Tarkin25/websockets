package ch.noseryoung.websockets.domain.user;

import ch.noseryoung.websockets.generic.AbstractEntityService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends AbstractEntityService<User>, UserDetailsService {
}
