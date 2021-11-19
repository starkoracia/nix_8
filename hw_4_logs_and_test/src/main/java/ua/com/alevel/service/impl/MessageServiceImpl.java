package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.MessageDaoImpl;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.MessageService;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class MessageServiceImpl implements MessageService {

    private static MessageServiceImpl instance;
    private final MessageDaoImpl messageDao;
    private Logger infoLogger;
    private Logger errorLogger;

    private MessageServiceImpl() {
        messageDao = MessageDaoImpl.getInstance();
        infoLogger = LoggerFactory.getLogger("info");
        errorLogger = LoggerFactory.getLogger("error");
    }

    public static MessageServiceImpl getInstance() {
        if (instance == null) {
            instance = new MessageServiceImpl();
        }
        return instance;
    }

    @Override
    public void create(Message message) throws UserPrincipalNotFoundException {
        infoLogger.info("start create message: {}", message);
        messageDao.create(message);
        infoLogger.info("message created successfully");
    }

    @Override
    public void update(Message message) throws UserPrincipalNotFoundException {
        infoLogger.info("start update message: {}", message);
        messageDao.update(message);
        infoLogger.info("message updated successfully");
    }

    @Override
    public void delete(Message message) throws UserPrincipalNotFoundException {
        infoLogger.info("start delete message: {}", message);
        messageDao.delete(message);
        infoLogger.info("message deleted successfully");
    }

    @Override
    public SimpleList<Message> findByAuthor(User author) throws UserPrincipalNotFoundException {
        infoLogger.info("start find message by author: {}", author);
        SimpleList<Message> byAuthor = messageDao.findByAuthor(author);
        infoLogger.info("message was find successfully: {}", byAuthor);
        return byAuthor;
    }

    @Override
    public SimpleList<Message> findByChannel(Channel channel) throws UserPrincipalNotFoundException {
        infoLogger.info("start find message by channel: {}", channel);
        SimpleList<Message> byChannel = messageDao.findByChannel(channel);
        infoLogger.info("message was find successfully: {}", byChannel);
        return byChannel;
    }

    @Override
    public Message findById(String id) throws UserPrincipalNotFoundException {
        infoLogger.info("start find message by id: {}", id);
        Message byId = messageDao.findById(id);
        infoLogger.info("message was find successfully: {}", byId);
        return byId;
    }

    @Override
    public SimpleList<Message> findAll() {
        return messageDao.findAll();
    }
}
