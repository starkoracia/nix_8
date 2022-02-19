package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoProductMaterial;
import ua.com.alevel.entities.ProductMaterial;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductMaterialDao implements DaoProductMaterial {

    EntityManager entityManager;

    public ProductMaterialDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(ProductMaterial productMaterial) {
        entityManager.persist(productMaterial);
        if(productMaterial.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(ProductMaterial productMaterial) {
        entityManager.merge(productMaterial);
    }

    @Override
    public void delete(ProductMaterial productMaterial) {
        entityManager.remove(productMaterial);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(pm) from ProductMaterial pm where pm.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<ProductMaterial> findById(Long id) {
        Query query = entityManager
                .createQuery("select pm from ProductMaterial pm where pm.id = :id")
                .setParameter("id", id);
        ProductMaterial productMaterial = (ProductMaterial) query.getSingleResult();
        return Optional.ofNullable(productMaterial);
    }

    @Override
    public List<ProductMaterial> findAll() {
        List<ProductMaterial> productMaterialList = (List<ProductMaterial>) entityManager
                .createQuery("select pm from ProductMaterial pm")
                .getResultList();
        return productMaterialList;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(pm) from ProductMaterial pm")
                .getSingleResult();
    }

}
