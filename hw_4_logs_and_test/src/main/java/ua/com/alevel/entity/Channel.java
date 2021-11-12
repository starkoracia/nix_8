package ua.com.alevel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel extends EntityBase {
    SimpleList<User> members;
}
