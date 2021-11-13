package ua.com.alevel.service;

import ua.com.alevel.entity.Channel;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface ChannelService extends ServiceBase<Channel> {
    Channel findByName(String channelName) throws UserPrincipalNotFoundException;
    boolean isChannelExist(String channelName) throws IOException;

}
