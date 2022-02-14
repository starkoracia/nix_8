package ua.com.alevel.hw_8_9_jpa_hibernate.services;

import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseService<ENTITY extends BaseEntity> {

    Boolean create(ENTITY entity);
    void update(ENTITY entity);
    void delete(ENTITY entity);
    boolean existById(Long id);
    Optional<ENTITY> findById(Long id);
    List<ENTITY> findAll();
    List<ENTITY> findAllFromRequest(PageDataRequest request);
    Long countNumberOfSearchMatches(PageDataRequest request);
    Long count();

}
