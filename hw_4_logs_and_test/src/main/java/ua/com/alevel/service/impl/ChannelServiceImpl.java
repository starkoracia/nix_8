package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.impl.ChannelDaoImpl;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Message;
import ua.com.alevel.service.ChannelService;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public class ChannelServiceImpl implements ChannelService {

    private static ChannelServiceImpl instance;
    private final ChannelDaoImpl channelDao;
    private Logger infoLogger;
    private Logger errorLogger;

    private ChannelServiceImpl() {
        channelDao = ChannelDaoImpl.getInstance();
        infoLogger = LoggerFactory.getLogger("info");
        errorLogger = LoggerFactory.getLogger("error");
    }

    public static ChannelServiceImpl getInstance() {
        if (instance == null) {
            instance = new ChannelServiceImpl();
        }
        return instance;
    }

    @Override
    public void create(Channel channel) {
        infoLogger.info("start create channel: {}", channel);
        channelDao.create(channel);
        infoLogger.info("channel created successfully");
    }

    @Override
    public void update(Channel channel) throws UserPrincipalNotFoundException {
        infoLogger.info("start update channel: {}", channel);
        channelDao.update(channel);
        infoLogger.info("channel updated successfully");
    }

    @Override
    public void delete(Channel channel) throws UserPrincipalNotFoundException {
        infoLogger.info("start delete channel: {}", channel);
        channelDao.delete(channel);
        MessageServiceImpl messageService = MessageServiceImpl.getInstance();
        SimpleList<Message> messages = null;
        try {
            infoLogger.info("start delete channel messages");
            messages = messageService.findByChannel(channel);
            for (Message message : messages) {
                messageService.delete(message);
            }
            infoLogger.info("channel messages deleted successfully");
        } catch (UserPrincipalNotFoundException ignored) {
            infoLogger.info("channel messages not find");
        }
        infoLogger.info("channel deleted successfully");
    }

    @Override
    public Channel findById(String id) throws UserPrincipalNotFoundException {
        infoLogger.info("start find channel by id: {}", id);
        Channel byId = channelDao.findById(id);
        infoLogger.info("channel was find successfully: {}", byId);
        return byId;
    }

    @Override
    public SimpleList<Channel> findAll() {
        return channelDao.findAll();
    }

    @Override
    public Channel findByName(String channelName) throws UserPrincipalNotFoundException {
        infoLogger.info("start find channel by channelName: {}", channelName);
        Channel byName = channelDao.findByName(channelName);
        infoLogger.info("channel was find successfully: {}", byName);
        return byName;
    }

    @Override
    public boolean isChannelExist(String channelName) throws IOException {
        Channel channelByName;
        try {
            channelByName = this.findByName(channelName);
        } catch (UserPrincipalNotFoundException e) {
            return false;
        }
        return true;
    }

}
