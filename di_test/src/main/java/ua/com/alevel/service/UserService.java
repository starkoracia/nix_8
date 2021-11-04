package ua.com.alevel.service;

import ua.com.alevel.entity.User;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface UserService extends ServiceBase<User>{
    boolean existByEmail(String email);
    User findByEmail(String email) throws UserPrincipalNotFoundException;
}
