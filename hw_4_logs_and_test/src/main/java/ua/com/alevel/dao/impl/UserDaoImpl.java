package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.UserDao;
import ua.com.alevel.db.impl.UserDBImpl;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl instance;
    private static UserDBImpl db;

    private UserDaoImpl() {
        db = UserDBImpl.getInstance();
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public void create(User user) throws UserPrincipalNotFoundException {
        db.create(user);
    }

    @Override
    public void update(User user) throws UserPrincipalNotFoundException {
        db.update(user);
    }

    @Override
    public void delete(User user) throws UserPrincipalNotFoundException {
        db.delete(user);
    }

    @Override
    public User findById(String id) throws UserPrincipalNotFoundException {
        return db.findById(id);
    }

    @Override
    public SimpleList<User> findAll() {
        return db.findAll();
    }

    @Override
    public boolean existByEmail(String email) {
        return db.existByEmail(email);
    }

    @Override
    public User findByEmail(String email) throws UserPrincipalNotFoundException {
        return db.findByEmail(email);
    }
}
