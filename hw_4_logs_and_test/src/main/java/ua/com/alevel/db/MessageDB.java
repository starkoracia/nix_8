package ua.com.alevel.db;

import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface MessageDB extends DBBase<Message>{
    SimpleList<Message> findByAuthor(User author) throws UserPrincipalNotFoundException;
    SimpleList<Message> findByChannel(Channel channel) throws UserPrincipalNotFoundException;
}
