package ua.com.alevel.db.impl;

import ua.com.alevel.db.ChannelDB;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.utils.DBHelperUtil;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class ChannelDBImpl implements ChannelDB {

    private final SimpleList<Channel> channels;
    private static ChannelDBImpl instance;
    
    private ChannelDBImpl() {
        this.channels = new SimpleList<>();
    }

    public static ChannelDBImpl getInstance() {
        if (instance == null) {
            instance = new ChannelDBImpl();
        }
        return instance;
    }

    @Override
    public void create(Channel channel) {
        channel.setId(DBHelperUtil.generateIdForEntity(channels));
        channels.add(channel);
    }

    @Override
    public void update(Channel channel) throws UserPrincipalNotFoundException {
        Channel channelFromDB = findById(channel.getId());
        channelFromDB.setChannelName(channel.getChannelName());
        channelFromDB.setMembers(channel.getMembers());
    }

    @Override
    public void delete(Channel channel) throws UserPrincipalNotFoundException {
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).getId().equals(channel.getId())) {
                channels.delete(i);
                return;
            }
        }
        throw new UserPrincipalNotFoundException("Ошибка удаления. Канал с таким ID не существует");
    }

    @Override
    public Channel findById(String id) throws UserPrincipalNotFoundException {
        for (Channel channel : channels) {
            if (channel.getId().equals(id)) {
                return channel;
            }
        }
        throw new UserPrincipalNotFoundException("Ошибка поиска. Канал с таким ID не существует");
    }

    @Override
    public SimpleList<Channel> findAll() {
        return channels;
    }
}
