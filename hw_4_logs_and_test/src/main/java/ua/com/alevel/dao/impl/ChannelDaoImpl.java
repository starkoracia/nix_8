package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.ChannelDao;
import ua.com.alevel.db.impl.ChannelDBImpl;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class ChannelDaoImpl implements ChannelDao {

    private static ChannelDaoImpl instance;
    private static ChannelDBImpl db;

    private ChannelDaoImpl() {
        db = ChannelDBImpl.getInstance();
    }

    public static ChannelDaoImpl getInstance() {
        if (instance == null) {
            instance = new ChannelDaoImpl();
        }
        return instance;
    }

    @Override
    public void create(Channel channel) {
        db.create(channel);
    }

    @Override
    public void update(Channel channel) throws UserPrincipalNotFoundException {
        db.update(channel);
    }

    @Override
    public void delete(Channel channel) throws UserPrincipalNotFoundException {
        db.delete(channel);
    }

    @Override
    public Channel findById(String id) throws UserPrincipalNotFoundException {
        return db.findById(id);
    }

    @Override
    public SimpleList<Channel> findAll() {
        return db.findAll();
    }

    @Override
    public Channel findByName(String channelName) throws UserPrincipalNotFoundException {
        return db.findByName(channelName);
    }
}
