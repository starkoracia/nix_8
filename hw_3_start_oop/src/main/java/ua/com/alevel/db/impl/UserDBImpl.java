package ua.com.alevel.db.impl;

import ua.com.alevel.db.UserDB;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.DBHelperUtil;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class UserDBImpl implements UserDB {

    private final SimpleList<User> users;
    private static UserDBImpl instance;

    private UserDBImpl() {
        this.users = new SimpleList<>();
    }

    public static UserDBImpl getInstance() {
        if (instance == null) {
            instance = new UserDBImpl();
        }
        return instance;
    }

    @Override
    public void create(User user) {
        user.setId(DBHelperUtil.generateIdForEntity(users));
        users.add(user);
    }

    @Override
    public void update(User user) throws UserPrincipalNotFoundException {
        User userFromDB = findById(user.getId());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPassword(user.getPassword());
        userFromDB.setName(user.getName());
    }

    @Override
    public void delete(User user) throws UserPrincipalNotFoundException {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(user.getId())) {
                users.delete(i);
                return;
            }
        }
        throw new UserPrincipalNotFoundException("Ошибка удаления. Пользователя с таким ID не существует");
    }

    @Override
    public User findById(String id) throws UserPrincipalNotFoundException {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
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
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new UserPrincipalNotFoundException("Ошибка поиска. Пользователя с таким Email не существует");
    }

}
