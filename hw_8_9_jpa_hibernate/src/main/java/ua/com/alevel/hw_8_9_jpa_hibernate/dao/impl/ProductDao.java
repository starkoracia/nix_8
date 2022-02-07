package ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.BaseDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Order;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Order_;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductDao implements BaseDao<Product> {

    private final EntityManager entityManager;

    @Autowired
    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(Product product) {
        entityManager.persist(product);
        if (product.getId() != null) {
            return true;
        }
        return false;
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
        Boolean isSortAsc = request.getIsSortAsc();
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> productCriteria = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = productCriteria.from(Product.class);

        Predicate searchPredicate = createSearchPredicate(search, criteriaBuilder, productRoot);
        javax.persistence.criteria.Order order = criteriaBuilder.asc(productRoot.get("id"));

        if(sortBy.equals("productName") || sortBy.equals("id") || sortBy.equals("price")) {
            if(isSortAsc) {
                order = criteriaBuilder.asc(productRoot.get(sortBy));
            } else {
                order = criteriaBuilder.desc(productRoot.get(sortBy));
            }
        }

        productCriteria
                .select(productRoot)
                .where(searchPredicate)
                .orderBy(order);

        List<Product> productList = entityManager
                .createQuery(productCriteria)
                .setFirstResult(limitFrom)
                .setMaxResults(limitAmount)
                .getResultList();

        return productList;
    }

    public List<Order> findOrdersFromProduct(Product product) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> orderCriteria = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = orderCriteria.from(Order.class);

        SetJoin<Order, Product> productsJoin = orderRoot.join(Order_.products);

        CriteriaBuilder.In<Product> in = criteriaBuilder.in(productsJoin).value(product);

        orderCriteria
                .select(orderRoot)
                .where(in);

        List<Order> resultList = entityManager
                .createQuery(orderCriteria)
                .getResultList();

        return resultList;
    }

    public Long countNumberOfSearchMatches(PageDataRequest request) {
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        Root<Product> productRoot = countCriteria.from(Product.class);

        Predicate searchPredicate = createSearchPredicate(search, criteriaBuilder, productRoot);

        countCriteria
                .select(criteriaBuilder.count(productRoot))
                .where(searchPredicate);

        Long count = entityManager
                .createQuery(countCriteria)
                .getSingleResult();

        return count;
    }


    private Predicate createSearchPredicate(String search, CriteriaBuilder criteriaBuilder, Root<Product> productRoot) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.like(productRoot.get(Product_.id).as(String.class), search));
        predicates.add(criteriaBuilder.like(productRoot.get(Product_.price).as(String.class), search));
        predicates.add(criteriaBuilder.like(productRoot.get(Product_.productName).as(String.class), search));

        return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
    }

    @Override
    public Long count() {
        return (long) entityManager.createQuery("select count(p) from Product p").getSingleResult();
    }

}
