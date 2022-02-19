package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoJobAndMaterial;
import ua.com.alevel.entities.JobAndMaterial;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JobAndMaterialDao implements DaoJobAndMaterial {

    EntityManager entityManager;

    public JobAndMaterialDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(JobAndMaterial jobAndMaterial) {
        entityManager.persist(jobAndMaterial);
        if(jobAndMaterial.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(JobAndMaterial jobAndMaterial) {
        entityManager.merge(jobAndMaterial);
    }

    @Override
    public void delete(JobAndMaterial jobAndMaterial) {
        entityManager.remove(jobAndMaterial);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(jm) from JobAndMaterial jm where jm.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<JobAndMaterial> findById(Long id) {
        Query query = entityManager
                .createQuery("select jm from JobAndMaterial jm where jm.id = :id")
                .setParameter("id", id);
        JobAndMaterial jobAndMaterial = (JobAndMaterial) query.getSingleResult();
        return Optional.ofNullable(jobAndMaterial);
    }

    @Override
    public List<JobAndMaterial> findAll() {
        List<JobAndMaterial> jobAndMaterialList = (List<JobAndMaterial>) entityManager
                .createQuery("select jm from JobAndMaterial jm")
                .getResultList();
        return jobAndMaterialList;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(jm) from JobAndMaterial jm")
                .getSingleResult();
    }

}
