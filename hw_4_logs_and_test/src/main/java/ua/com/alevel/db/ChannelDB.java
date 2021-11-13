package ua.com.alevel.db;

import ua.com.alevel.entity.Channel;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface ChannelDB extends DBBase<Channel> {
    Channel findByName(String channelName) throws UserPrincipalNotFoundException;
}
