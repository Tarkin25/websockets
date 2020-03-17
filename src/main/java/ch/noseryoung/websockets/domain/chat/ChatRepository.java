package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.core.generic.AbstractEntityRepository;
import ch.noseryoung.websockets.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChatRepository extends AbstractEntityRepository<Chat> {

    Collection<Chat> findAllByDeletedFalseAndUsersContaining(User user);

}
