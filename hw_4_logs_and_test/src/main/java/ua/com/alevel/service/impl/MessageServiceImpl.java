package ua.com.alevel.service.impl;

import ua.com.alevel.dao.impl.MessageDaoImpl;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.MessageService;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class MessageServiceImpl implements MessageService {
    private static MessageServiceImpl instance;
    private final MessageDaoImpl messageDao;

    private MessageServiceImpl() {
        messageDao = MessageDaoImpl.getInstance();
    }

    public static MessageServiceImpl getInstance() {
        if (instance == null) {
            instance = new MessageServiceImpl();
        }
        return instance;
    }

    @Override
    public void create(Message message) {
        messageDao.create(message);
    }

    @Override
    public void update(Message message) throws UserPrincipalNotFoundException {
        messageDao.update(message);
    }

    @Override
    public void delete(Message message) throws UserPrincipalNotFoundException {
        messageDao.delete(message);
    }

    @Override
    public SimpleList<Message> findByAuthor(User author) throws UserPrincipalNotFoundException {
        return messageDao.findByAuthor(author);
    }

    @Override
    public Message findById(String id) throws UserPrincipalNotFoundException {
        return messageDao.findById(id);
    }

    @Override
    public SimpleList<Message> findAll() {
        return messageDao.findAll();
    }
}
