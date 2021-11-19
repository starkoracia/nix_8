package ua.com.alevel.service.impl;

import org.antlr.v4.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.UserDaoImpl;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private static UserDaoImpl userDao;
    private Logger infoLogger;
    private Logger errorLogger;

    private UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
        infoLogger = LoggerFactory.getLogger("info");
        errorLogger = LoggerFactory.getLogger("error");
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public User findById(String id) throws UserPrincipalNotFoundException {
        infoLogger.info("start find user by id: {}", id);
        User userById = userDao.findById(id);
        infoLogger.info("user was find successfully: {}", userById);
        return userById;
    }

    @Override
    public SimpleList<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void create(User user) throws UserPrincipalNotFoundException {
        infoLogger.info("start create user: {}", user);
        userDao.create(user);
        infoLogger.info("user created successfully");
    }

    @Override
    public void update(User user) throws UserPrincipalNotFoundException {
        infoLogger.info("start update user: {}", user);
        userDao.update(user);
        infoLogger.info("user updated successfully");
    }

    @Override
    public void delete(User user) throws UserPrincipalNotFoundException {
        infoLogger.info("start delete user: {}", user);
        userDao.delete(user);
        MessageServiceImpl messageService = MessageServiceImpl.getInstance();
        SimpleList<Message> messages = null;
        try {
            infoLogger.info("start delete user messages");
            messages = messageService.findByAuthor(user);
            for (Message message : messages) {
                messageService.delete(message);
            }
            infoLogger.info("user messages deleted successfully");
        } catch (UserPrincipalNotFoundException ignored) {
            infoLogger.info("user messages not find");
        }
        infoLogger.info("user deleted successfully");
    }

    @Override
    public boolean existByEmail(String email) {
        return userDao.existByEmail(email);
    }

    @Override
    public User findByEmail(String email) throws UserPrincipalNotFoundException {
        infoLogger.info("start find user by email: {}", email);
        User userByEmail = userDao.findByEmail(email);
        infoLogger.info("user was find successfully: {}", userByEmail);
        return userByEmail;
    }

}
