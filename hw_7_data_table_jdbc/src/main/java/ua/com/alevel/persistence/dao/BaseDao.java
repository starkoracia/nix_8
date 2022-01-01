package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface BaseDao<ENTITY extends BaseEntity, ID> {
    void create(ENTITY entity);

    void update(ENTITY entity);

    void delete(ENTITY entity);

    ENTITY findById(ID id);

    boolean existById(ID id);

    List<ENTITY> findAll();

    long count();

}
