package ua.com.alevel.service.impl;

import ua.com.alevel.dao.impl.ChannelDaoImpl;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.service.ChannelService;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class ChannelServiceImpl implements ChannelService {
    
    private static ChannelServiceImpl instance;
    private final ChannelDaoImpl channelDao;

    private ChannelServiceImpl() {
        channelDao = ChannelDaoImpl.getInstance();
    }

    public static ChannelServiceImpl getInstance() {
        if (instance == null) {
            instance = new ChannelServiceImpl();
        }
        return instance;
    }
    
    @Override
    public void create(Channel channel) {
        channelDao.create(channel);
    }

    @Override
    public void update(Channel channel) throws UserPrincipalNotFoundException {
        channelDao.update(channel);
    }

    @Override
    public void delete(Channel channel) throws UserPrincipalNotFoundException {
        channelDao.delete(channel);
    }

    @Override
    public Channel findById(String id) throws UserPrincipalNotFoundException {
        return channelDao.findById(id);
    }

    @Override
    public SimpleList<Channel> findAll() {
        return channelDao.findAll();
    }
}
