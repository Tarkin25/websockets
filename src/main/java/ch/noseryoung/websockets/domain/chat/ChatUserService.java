package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.user.User;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

public interface ChatUserService {

    Set<User> findUsers(String chatId) throws NoSuchElementException;

    User addToChat(String chatId, String userId) throws NoSuchElementException;

    void removeFromChat(String chatId, String userId) throws NoSuchElementException;

    Collection<User> findUsersNotIn(String chatId) throws NoSuchElementException;

}
