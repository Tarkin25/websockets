package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.user.User;

import java.util.NoSuchElementException;

public interface ChatService {

    Chat create(Chat chat, User creator);

    Chat updateById(String id, Chat chat) throws NoSuchElementException;

    void deleteById(String id) throws NoSuchElementException;

}
