package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoProductCategory;
import ua.com.alevel.entities.ProductCategory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductCategoryDao implements DaoProductCategory {

    EntityManager entityManager;

    public ProductCategoryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(ProductCategory category) {
        entityManager.persist(category);
        if(category.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(ProductCategory category) {
        entityManager.merge(category);
    }

    @Override
    public void delete(ProductCategory category) {
        entityManager.remove(category);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(cg) from ProductCategory cg where cg.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<ProductCategory> findById(Long id) {
        Query query = entityManager
                .createQuery("select cg from ProductCategory cg where cg.id = :id")
                .setParameter("id", id);
        ProductCategory category = (ProductCategory) query.getSingleResult();
        return Optional.ofNullable(category);
    }

    @Override
    public List<ProductCategory> findAll() {
        List<ProductCategory> categoryList = (List<ProductCategory>) entityManager
                .createQuery("select cg from ProductCategory cg")
                .getResultList();
        return categoryList;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(cg) from ProductCategory cg")
                .getSingleResult();
    }

}
