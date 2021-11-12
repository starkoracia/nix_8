package ua.com.alevel.dao;

import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface MessageDao extends DaoBase<Message>{
    SimpleList<Message> findByAuthor(User author) throws UserPrincipalNotFoundException;
}
