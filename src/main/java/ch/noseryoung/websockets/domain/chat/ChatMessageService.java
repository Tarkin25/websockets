package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.message.Message;
import ch.noseryoung.websockets.domain.user.User;

import java.util.List;
import java.util.NoSuchElementException;

public interface ChatMessageService {

    List<Message> findMessages(String chatId) throws NoSuchElementException;

    Message addMessage(String chatId, Message message, User creator) throws NoSuchElementException;

}
