package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.domain.message.Message;
import ch.noseryoung.websockets.domain.message.MessageService;
import ch.noseryoung.websockets.domain.message.dto.MessageMapper;
import ch.noseryoung.websockets.domain.user.User;
import ch.noseryoung.websockets.domain.user.UserService;
import ch.noseryoung.websockets.generic.AbstractEntityServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChatMessageServiceImpl extends AbstractEntityServiceImpl<Chat> implements ChatMessageService {

    private MessageService messageService;
    private SimpMessagingTemplate simpMessagingTemplate;
    private MessageMapper messageMapper;

    @Autowired
    public ChatMessageServiceImpl(ChatRepository repository, Logger logger, UserService userService, MessageService messageService, SimpMessagingTemplate simpMessagingTemplate, MessageMapper messageMapper) {
        super(repository, logger);
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<Message> findMessages(String chatId) throws NoSuchElementException {
        logger.debug("Attempting to find Messages in {} with ID '{}'", singleEntity(), chatId);

        List<Message> messages = findById(chatId).getMessages();

        logger.debug("Loaded Messages");

        return messages;
    }

    @Override
    public Message addMessage(String chatId, Message message, User creator) throws NoSuchElementException {
        logger.debug("Attempting to add new Message to {} with ID '{}'", singleEntity(), chatId);

        Chat chat = findById(chatId);

        message.setFrom(creator);
        message = messageService.create(message);

        chat.getMessages().add(message);
        repository.save(chat);

        logger.debug("Added Message to {}" ,singleEntity());

        simpMessagingTemplate.convertAndSend(String.format("/chats/%s/messages", chatId), messageMapper.toDTO(message));

        logger.debug("Broadcasted new Message");
        return message;
    }
}
