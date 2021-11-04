package ua.com.alevel.db;

import ua.com.alevel.entity.EntityBase;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface DBBase<E extends EntityBase> {
    void create(E entity);
    void update(E entity) throws UserPrincipalNotFoundException;
    void delete(E entity) throws UserPrincipalNotFoundException;
    E findById(String id) throws UserPrincipalNotFoundException;
    SimpleList<E> findAll();
}
