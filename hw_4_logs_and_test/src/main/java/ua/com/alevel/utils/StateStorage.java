package ua.com.alevel.utils;

import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.User;

public class StateStorage {
    private static StateStorage instance;
    private User authUser;
    private Channel channel;

    private StateStorage() { }

    public static StateStorage getInstance() {
        if(instance == null) {
            instance = new StateStorage();
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
