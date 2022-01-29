package ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.BaseDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductDao implements BaseDao<Product> {

    private final EntityManager entityManager;

    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    public void delete(Product product) {
        entityManager.remove(product);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(p) from Product p where p.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Query query = entityManager
                .createQuery("select p from Product p left join fetch p.orders where p.id = :id")
                .setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = (List<Product>) entityManager
                .createQuery("select p from Product p")
                .getResultList();
        return productList;
    }

    @Override
    public List<Product> findAllFromRequest(PageDataRequest request) {
        int limitAmount = request.getNumberOfElementsOnPage();
        int limitFrom = (request.getPageNumber() - 1) * limitAmount;
        String sortBy = request.getSortBy();
        String ascDesc = request.getIsSortAsc() ? "asc" : "desc";
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> productCriteria = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = productCriteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();


//        predicates.add(criteriaBuilder.like(productRoot.get("id"), search));

        productCriteria.select(productRoot);

        List<Product> productList = entityManager
                .createQuery(productCriteria)
                .setFirstResult(limitFrom)
                .setMaxResults(limitAmount)
                .getResultList();

        return productList;
    }

    @Override
    public long count() {
        return (long) entityManager.createQuery("select count(p) from Product p").getSingleResult();
    }

}
