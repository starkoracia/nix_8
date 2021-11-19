package ua.com.alevel.db.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.db.UserDB;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.DBHelperUtil;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class UserDBImpl implements UserDB {

    private final SimpleList<User> users;
    private static UserDBImpl instance;
    private Logger infoLogger;
    private Logger errorLogger;

    private UserDBImpl() {
        this.users = new SimpleList<>();
        infoLogger = LoggerFactory.getLogger("info");
        errorLogger = LoggerFactory.getLogger("error");
    }

    public static UserDBImpl getInstance() {
        if (instance == null) {
            instance = new UserDBImpl();
        }
        return instance;
    }

    @Override
    public void create(User user) throws UserPrincipalNotFoundException {
        infoLogger.info("userDB start create user: {}", user);
        if (user != null) {
            user.setId(DBHelperUtil.generateIdForEntity(users));
            users.add(user);
            infoLogger.info("userDB user created successfully");
            return;
        }
        errorLogger.error("userDB user create error {}", UserPrincipalNotFoundException.class);
        throw new UserPrincipalNotFoundException("Ошибка зоздания пользователя");
    }

    @Override
    public void update(User user) throws UserPrincipalNotFoundException {
        infoLogger.info("userDB start update user: {}", user);
        User userFromDB = findById(user.getId());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPassword(user.getPassword());
        userFromDB.setName(user.getName());
        infoLogger.info("userDB user update successfully");
    }

    @Override
    public void delete(User user) throws UserPrincipalNotFoundException {
        infoLogger.info("userDB start delete user: {}", user);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.delete(i);
                infoLogger.info("userDB user delete successfully");
                return;
            }
        }
        errorLogger.error("userDB user delete error {}", UserPrincipalNotFoundException.class);
        throw new UserPrincipalNotFoundException("Ошибка удаления. Пользователя с таким ID не существует");
    }

    @Override
    public User findById(String id) throws UserPrincipalNotFoundException {
        infoLogger.info("userDB start find user by id: {}", id);
        for (User user : users) {
            if (user.getId().equals(id)) {
                infoLogger.info("userDB find user by id successfully, user: {}", user);
                return user;
            }
        }
        errorLogger.error("userDB find user by id error {}", UserPrincipalNotFoundException.class);
        throw new UserPrincipalNotFoundException("Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Override
    public SimpleList<User> findAll() {
        return users;
    }


    @Override
    public boolean existByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findByEmail(String email) throws UserPrincipalNotFoundException {
        infoLogger.info("userDB start find user by email: {}", email);
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                infoLogger.info("userDB find user by email successfully, user: {}", user);
                return user;
            }
        }
        errorLogger.error("userDB find user by email error {}", UserPrincipalNotFoundException.class);
        throw new UserPrincipalNotFoundException("Ошибка поиска. Пользователя с таким Email не существует");
    }

}
