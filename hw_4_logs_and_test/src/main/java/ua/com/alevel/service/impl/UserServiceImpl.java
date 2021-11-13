package ua.com.alevel.service.impl;

import ua.com.alevel.dao.impl.UserDaoImpl;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private static UserDaoImpl userDao;

    private UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public User findById(String id) throws UserPrincipalNotFoundException {
        return userDao.findById(id);
    }

    @Override
    public SimpleList<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public void update(User user) throws UserPrincipalNotFoundException {
        userDao.update(user);
    }

    @Override
    public void delete(User user) throws UserPrincipalNotFoundException {
        userDao.delete(user);
        MessageServiceImpl messageService = MessageServiceImpl.getInstance();
        SimpleList<Message> messages = messageService.findByAuthor(user);
        for (Message message : messages) {
            messageService.delete(message);
        }
    }

    @Override
    public boolean existByEmail(String email) {
        return userDao.existByEmail(email);
    }

    @Override
    public User findByEmail(String email) throws UserPrincipalNotFoundException {
        return userDao.findByEmail(email);
    }

}
