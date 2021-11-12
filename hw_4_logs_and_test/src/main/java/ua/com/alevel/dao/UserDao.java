package ua.com.alevel.dao;

import ua.com.alevel.entity.User;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface UserDao extends DaoBase<User> {
    boolean existByEmail(String email);
    User findByEmail(String email) throws UserPrincipalNotFoundException;

}
