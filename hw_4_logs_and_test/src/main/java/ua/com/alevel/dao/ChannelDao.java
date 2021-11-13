package ua.com.alevel.dao;

import ua.com.alevel.entity.Channel;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface ChannelDao extends DaoBase<Channel> {
    Channel findByName(String channelName) throws UserPrincipalNotFoundException;
}
