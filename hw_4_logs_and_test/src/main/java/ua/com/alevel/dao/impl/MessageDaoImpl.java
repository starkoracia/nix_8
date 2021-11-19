package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.MessageDao;
import ua.com.alevel.db.impl.MessageDBImpl;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class MessageDaoImpl implements MessageDao {

    private static MessageDaoImpl instance;
    private static MessageDBImpl db;

    private MessageDaoImpl() {
        db = MessageDBImpl.getInstance();
    }

    public static MessageDaoImpl getInstance() {
        if (instance == null) {
            instance = new MessageDaoImpl();
        }
        return instance;
    }

    @Override
    public void create(Message message) throws UserPrincipalNotFoundException {
        db.create(message);
    }

    @Override
    public void update(Message message) throws UserPrincipalNotFoundException {
        db.update(message);
    }

    @Override
    public void delete(Message message) throws UserPrincipalNotFoundException {
        db.delete(message);
    }

    @Override
    public SimpleList<Message> findByAuthor(User author) throws UserPrincipalNotFoundException {
        return db.findByAuthor(author);
    }

    @Override
    public SimpleList<Message> findByChannel(Channel channel) throws UserPrincipalNotFoundException {
        return db.findByChannel(channel);
    }

    @Override
    public Message findById(String id) throws UserPrincipalNotFoundException {
        return db.findById(id);
    }

    @Override
    public SimpleList<Message> findAll() {
        return db.findAll();
    }
}
