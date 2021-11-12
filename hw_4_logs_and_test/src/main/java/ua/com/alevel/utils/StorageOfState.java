package ua.com.alevel.utils;

import ua.com.alevel.entity.User;

import java.nio.channels.Channel;

public class StorageOfState {
    private static StorageOfState instance;
    private User authUser;
    private Channel channel;

    private StorageOfState() { }

    public static StorageOfState getInstance() {
        if(instance == null) {
            instance = new StorageOfState();
        }
        return instance;
    }

    public Channel getCurrentChannel() {
        return channel;
    }

    public User getAuthUser() {
        return authUser;
    }

    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
