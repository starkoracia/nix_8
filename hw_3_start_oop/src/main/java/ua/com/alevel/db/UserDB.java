package ua.com.alevel.db;

import ua.com.alevel.entity.User;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface UserDB extends DBBase<User>{
    boolean existByEmail(String email);
    User findByEmail(String email) throws UserPrincipalNotFoundException;
}
