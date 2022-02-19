package ua.com.alevel.dao;

import ua.com.alevel.entities.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao <ENTITY extends BaseEntity> {
    Boolean create(ENTITY entity);
    void update(ENTITY entity);
    void delete(ENTITY entity);
    boolean existById(Long id);
    Optional<ENTITY> findById(Long id);
    List<ENTITY> findAll();
    Long count();
}
