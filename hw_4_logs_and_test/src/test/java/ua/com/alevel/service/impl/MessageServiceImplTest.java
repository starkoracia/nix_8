package ua.com.alevel.service.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class MessageServiceImplTest {

    private static MessageServiceImpl messageService;
    private static String firstMessageId;
    private static Message firstMessage;
    private static Message secondMessage;
    private static Message thirdMessage;
    private static User author;
    private static Channel channel;


    @BeforeClass
    public static void setUp() throws UserPrincipalNotFoundException {
        messageService = MessageServiceImpl.getInstance();
        author = new User("email", "password", "name");
        channel = new Channel("channelName");
        firstMessage = new Message("First message", author, channel);
        secondMessage = new Message("Second message", author, channel);
        thirdMessage = new Message("Third message", author, channel);
        messageService.create(firstMessage);
        messageService.create(secondMessage);
        messageService.create(thirdMessage);
        SimpleList<Message> allMessages = messageService.findAll();
        firstMessageId = allMessages.get(0).getId();
    }

    @Test
    @Order(1)
    public void findByIdShouldReturnMessageWhenIdEqualsMessageIdWhichContainsInMessageList() throws UserPrincipalNotFoundException {
        Message messageById = messageService.findById(firstMessageId);

        Assertions.assertEquals(messageById.getId(), firstMessageId);
    }

    @Test
    public void findByIdShouldThrowUserPrincipalExceptionWhenIdIsNull() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> messageService.findById(null));
    }

    @Test
    @Order(0)
    public void findAllShouldReturnSimpleListOfMessages() {
        SimpleList<Message> messageSimpleList = messageService.findAll();
        Assertions.assertEquals(SimpleList.class, messageSimpleList.getClass());
    }

    @Test
    public void createShouldAddMessageToMessageServiceList() throws UserPrincipalNotFoundException {
        int size = messageService.findAll().size();
        messageService.create(new Message("em", author, channel));
        Assertions.assertEquals(size + 1, messageService.findAll().size());
    }

    @Test
    public void createShouldThrowException() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> messageService.create(null),
                "Ошибка зоздания комента");
    }

    @Test
    @Order(2)
    public void updateShouldUpdateMessageInMessageServiceList() throws UserPrincipalNotFoundException {
        firstMessage = messageService.findById(firstMessageId);
        firstMessage.setText("New text");
        messageService.update(firstMessage);
        Message updatedMessage = messageService.findById(firstMessageId);
        Assertions.assertEquals(firstMessage, updatedMessage);
    }

    @Test
    public void updateShouldThrowUserPrincipalNotFoundExceptionWhenMessageNotFoundInMessageServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> messageService.findById("firstMessageId"),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Test
    public void deleteShouldDeletedMessageFromMessageServiceList() throws UserPrincipalNotFoundException {
        Message messageById = messageService.findById(firstMessageId);
        int size = messageService.findAll().size();
        messageService.delete(messageById);
        Assertions.assertEquals(size - 1, messageService.findAll().size());
    }

    @Test
    public void deleteShouldThrowUserPrincipalNotFoundExceptionWhenDeleteNotExistingMessageFromMessageServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> messageService.findById("firstMessageId"),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Test
    public void findByAuthorShouldReturnMessagesSimpleListWhenAuthorIsExistInMessageServiceList() throws UserPrincipalNotFoundException {
        SimpleList<Message> byAuthor = messageService.findByAuthor(author);
        Assertions.assertEquals(SimpleList.class, byAuthor.getClass());
    }

    @Test
    public void findByAuthorShouldThrowUserPrincipalNotFoundExceptionWhenAuthorIsNotExistInUserServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> messageService.findByAuthor(new User()),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

}