package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.user.User;
import ch.noseryoung.websockets.domain.user.UserService;
import ch.noseryoung.websockets.core.generic.AbstractEntityServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ChatServiceImpl extends AbstractEntityServiceImpl<Chat> implements ChatService {

    protected UserService userService;

    @Autowired
    public ChatServiceImpl(ChatRepository repository, Logger logger, UserService userService) {
        super(repository, logger);
        this.userService = userService;
    }

    @Override
    public Chat create(Chat chat, User creator) {
        logger.debug("Attempting to create new {}", singleEntity());

        chat.setId(null);
        chat = repository.save(chat);

        logger.debug("Created {}. New ID is '{}'", singleEntity(), chat.getId());

        addToChat(creator, chat);

        return chat;
    }

    private void addToChat(User user, Chat chat) {
        logger.debug("Attempting to add User with ID '{}' to new {}", user.getId(), singleEntity());

        chat.getUsers().add(user);
        user.getChats().add(chat);

        repository.save(chat);
        userService.save(user);

        logger.debug("Added User to {}", singleEntity());
    }
}
