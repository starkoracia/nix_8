package ua.com.alevel.utils;

import ua.com.alevel.entity.EntityBase;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.util.UUID;

public final class DBHelperUtil {

    private DBHelperUtil() { }

    public static String generateIdForEntity(SimpleList<? extends EntityBase> entities) {
        String id = UUID.randomUUID().toString();
        for (EntityBase eBase : entities) {
            if (eBase.getId().equals(id)) {
                return generateIdForEntity(entities);
            }
        }
        return id;
    }

}
