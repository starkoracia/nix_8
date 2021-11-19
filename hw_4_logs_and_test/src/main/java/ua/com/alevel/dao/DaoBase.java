package ua.com.alevel.dao;

import ua.com.alevel.entity.EntityBase;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface DaoBase<E extends EntityBase> {
    void create(E entity) throws UserPrincipalNotFoundException;
    void update(E entity) throws UserPrincipalNotFoundException;
    void delete(E entity) throws UserPrincipalNotFoundException;
    E findById(String id) throws UserPrincipalNotFoundException;
    SimpleList<E> findAll();
}
