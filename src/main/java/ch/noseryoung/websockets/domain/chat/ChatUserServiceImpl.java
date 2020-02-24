package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.user.User;
import ch.noseryoung.websockets.domain.user.UserService;
import ch.noseryoung.websockets.generic.AbstractEntityServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ChatUserServiceImpl extends AbstractEntityServiceImpl<Chat> implements ChatUserService {

    private UserService userService;

    @Autowired
    public ChatUserServiceImpl(ChatRepository repository, Logger logger, UserService userService) {
        super(repository, logger);
        this.userService = userService;
    }

    @Override
    public Set<User> findUsers(String chatId) throws NoSuchElementException {
        logger.debug("Attempting to find Users in {} with ID '{}'", singleEntity(), chatId);

        Chat chat = findById(chatId);

        logger.debug("Loaded Users");
        return chat.getUsers();
    }

    @Override
    public User addToChat(String chatId, String userId) throws NoSuchElementException {
        logger.debug("Attempting to add User with ID '{}' to {} with ID '{}'", userId, singleEntity(), chatId);

        Chat chat = findById(chatId);
        User user = userService.findById(userId);

        chat.getUsers().add(user);
        user.getChats().add(chat);

        repository.save(chat);
        userService.save(user);

        logger.debug("Added User to {}", singleEntity());

        return user;
    }

    @Override
    public void removeFromChat(String chatId, String userId) throws NoSuchElementException {
        logger.debug("Attempting to remove User with ID '{}' from {} with ID '{}'", userId, singleEntity(), chatId);

        Chat chat = findById(chatId);
        User user = userService.findById(userId);

        chat.getUsers().remove(user);
        user.getChats().remove(chat);

        repository.save(chat);
        userService.save(user);

        logger.debug("Removed User from {}", singleEntity());
    }
}
