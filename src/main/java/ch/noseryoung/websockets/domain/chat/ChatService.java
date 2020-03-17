package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.core.generic.AbstractEntityService;
import ch.noseryoung.websockets.domain.user.User;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface ChatService extends AbstractEntityService<Chat> {

    Chat create(Chat chat, User creator);

    Collection<Chat> findAllByUser(User user);

}
