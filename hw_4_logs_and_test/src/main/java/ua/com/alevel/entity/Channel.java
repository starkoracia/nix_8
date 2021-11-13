package ua.com.alevel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Channel extends EntityBase {
    private String channelName;
    private SimpleList<User> members;

    public Channel(String channelName) {
        this.channelName = channelName;
        members = new SimpleList<>();
    }

    public boolean contain(User authUser) {
        for (User member : members) {
            if (member.equals(authUser)) {
                return true;
            }
        }
        return false;
    }

}
