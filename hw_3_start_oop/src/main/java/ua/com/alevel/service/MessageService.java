package ua.com.alevel.service;

import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface MessageService extends ServiceBase<Message> {
    SimpleList<Message> findByAuthor(User author) throws UserPrincipalNotFoundException;
}
