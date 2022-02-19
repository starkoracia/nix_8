package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoJob;
import ua.com.alevel.entities.Job;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JobDao implements DaoJob {

    EntityManager entityManager;

    public JobDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(Job job) {
        entityManager.persist(job);
        if(job.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Job job) {
        entityManager.merge(job);
    }

    @Override
    public void delete(Job job) {
        entityManager.remove(job);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(job) from Job job where job.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Job> findById(Long id) {
        Query query = entityManager
                .createQuery("select job from Job job where job.id = :id")
                .setParameter("id", id);
        Job job = (Job) query.getSingleResult();
        return Optional.ofNullable(job);
    }

    @Override
    public List<Job> findAll() {
        List<Job> jobList = (List<Job>) entityManager
                .createQuery("select job from Job job")
                .getResultList();
        return jobList;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(job) from Job job")
                .getSingleResult();
    }

}
