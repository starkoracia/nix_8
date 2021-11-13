package ua.com.alevel.db.impl;

import ua.com.alevel.db.MessageDB;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.DBHelperUtil;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class MessageDBImpl implements MessageDB {

    private final SimpleList<Message> messages;
    private static MessageDBImpl instance;

    private MessageDBImpl() {
        this.messages = new SimpleList<>();
    }

    public static MessageDBImpl getInstance() {
        if (instance == null) {
            instance = new MessageDBImpl();
        }
        return instance;
    }

    @Override
    public void create(Message message) {
        message.setId(DBHelperUtil.generateIdForEntity(messages));
        messages.add(message);
    }

    @Override
    public void update(Message message) throws UserPrincipalNotFoundException {
        Message messageFromDB = findById(message.getId());
        messageFromDB.setText(message.getText());
        messageFromDB.setAuthor(message.getAuthor());
    }

    @Override
    public void delete(Message message) throws UserPrincipalNotFoundException {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getId().equals(message.getId())) {
                messages.delete(i);
                return;
            }
        }
        throw new UserPrincipalNotFoundException("Ошибка удаления. Комментария с таким ID не существует");
    }

    @Override
    public Message findById(String id) throws UserPrincipalNotFoundException {
        for (Message message : messages) {
            if (message.getId().equals(id)) {
                return message;
            }
        }
        throw new UserPrincipalNotFoundException("Ошибка поиска. Комментария с таким ID не существует");
    }

    @Override
    public SimpleList<Message> findAll() {
        return messages;
    }

    @Override
    public SimpleList<Message> findByAuthor(User author) throws UserPrincipalNotFoundException {
        SimpleList<Message> messageList = new SimpleList<>();
        for (Message message : messages) {
            if (message.getAuthor().equals(author)) {
                messageList.add(message);
            }
        }
        if(messageList.size() == 0) {
            throw new UserPrincipalNotFoundException("Ошибка поиска. Комментариев этого автора не существует");
        }
        return messageList;
    }

    @Override
    public SimpleList<Message> findByChannel(Channel channel) throws UserPrincipalNotFoundException {
        SimpleList<Message> messageList = new SimpleList<>();
        for (Message message : messages) {
            if (message.getChannel().equals(channel)) {
                messageList.add(message);
            }
        }
        if(messageList.size() == 0) {
            throw new UserPrincipalNotFoundException("Ошибка поиска. Комментариев этого автора не существует");
        }
        return messageList;
    }

}
