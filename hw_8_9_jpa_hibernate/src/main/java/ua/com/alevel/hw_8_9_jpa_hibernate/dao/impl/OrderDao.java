package ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.BaseDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.*;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Order;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDao implements BaseDao<Order> {
    EntityManager entityManager;

    public OrderDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(Order order) {
        entityManager.persist(order);
        if(order.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(order) from Order order where order.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Order> findById(Long id) {
        Query query = entityManager
                .createQuery("select o from Order as o left join fetch o.products where o.id = :id")
                .setParameter("id", id);
        Order order = (Order) query.getSingleResult();
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orderList = (List<Order>) entityManager
                .createQuery("select order from Order order")
                .getResultList();
        return orderList;
    }

    public List<Product> findProductsFromOrder(Order order) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> productCriteria = criteriaBuilder.createQuery(Product.class);
        Root<Order> orderRoot = productCriteria.from(Order.class);

        Predicate likeOrderId = criteriaBuilder.like(orderRoot.get(Order_.id).as(String.class), order.getId().toString());

        SetJoin<Order, Product> productsJoin = orderRoot.join(Order_.products);

        productCriteria
                .select(productsJoin)
                .where(likeOrderId);

        List<Product> resultList = entityManager
                .createQuery(productCriteria)
                .getResultList();

        return resultList;
    }

    public Long countProductsFromOrder(Order order) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> productCriteria = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = productCriteria.from(Order.class);

        Predicate likeOrderId = criteriaBuilder.like(orderRoot.get(Order_.id).as(String.class), order.getId().toString());

        SetJoin<Order, Product> productsJoin = orderRoot.join(Order_.products);

        productCriteria
                .select(criteriaBuilder.count(productsJoin))
                .where(likeOrderId);

        Long count = entityManager
                .createQuery(productCriteria)
                .getSingleResult();

        return count;
    }


//    @Override
//    public List<Order> findAllFromRequest(PageDataRequest request) {
//        int limitAmount = request.getNumberOfElementsOnPage();
//        int limitFrom = (request.getPageNumber() - 1) * limitAmount;
//        String sortBy = request.getSortBy();
//        Boolean isSortAsc = request.getIsSortAsc();
//        String search = "%" + request.getSearchString() + "%";
//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Order> orderCriteria = criteriaBuilder.createQuery(Order.class);
//        Root<Order> orderRoot = orderCriteria.from(Order.class);
//
//        Join<Order, Customer> customerJoin = orderRoot.join(Order_.customer);
//        Predicate searchPredicate = createSearchPredicate(search, criteriaBuilder, orderRoot, customerJoin);
//        javax.persistence.criteria.Order order = criteriaBuilder.asc(orderRoot.get("id"));
//
//        if(sortBy.equals("customer")) {
//            if(isSortAsc) {
//                order = criteriaBuilder.asc(customerJoin.get(Customer_.firstName));
//            } else {
//                order = criteriaBuilder.desc(customerJoin.get(Customer_.firstName));
//            }
//        } else if (sortBy.equals("id")) {
//            if(isSortAsc) {
//                order = criteriaBuilder.asc(orderRoot.get(Order_.id));
//            } else {
//                order = criteriaBuilder.desc(orderRoot.get(Order_.id));
//            }
//        }
//
//        orderCriteria
//                .select(orderRoot)
//                .where(searchPredicate)
//                .orderBy(order);
//
//        List<Order> orderList = entityManager
//                .createQuery(orderCriteria)
//                .setFirstResult(limitFrom)
//                .setMaxResults(limitAmount)
//                .getResultList();
//
//        return orderList;
//    }

@Override
    public List<Order> findAllFromRequest(PageDataRequest request) {
        int limitAmount = request.getNumberOfElementsOnPage();
        int limitFrom = (request.getPageNumber() - 1) * limitAmount;
        String sortBy = request.getSortBy();
        Boolean isSortAsc = request.getIsSortAsc();
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> orderCriteria = criteriaBuilder.createQuery(Tuple.class);
        Root<Order> orderRoot = orderCriteria.from(Order.class);

        Join<Order, Customer> customerJoin = orderRoot.join(Order_.customer);
        Predicate searchPredicate = createSearchPredicate(search, criteriaBuilder, orderRoot, customerJoin);
        javax.persistence.criteria.Order order = criteriaBuilder.asc(orderRoot.get(Order_.id));

        if(sortBy.equals("customer")) {
            if(isSortAsc) {
                order = criteriaBuilder.asc(customerJoin.get(Customer_.firstName));
            } else {
                order = criteriaBuilder.desc(customerJoin.get(Customer_.firstName));
            }
        } else if (sortBy.equals("id")) {
            if(isSortAsc) {
                order = criteriaBuilder.asc(orderRoot.get(Order_.id));
            } else {
                order = criteriaBuilder.desc(orderRoot.get(Order_.id));
            }
        }

        SetJoin<Order, Product> productsJoin = orderRoot.join(Order_.products, JoinType.LEFT);
        orderCriteria.groupBy(orderRoot.get(Order_.id));

        orderCriteria.multiselect(orderRoot, criteriaBuilder.count(productsJoin))
                .where(searchPredicate)
                .orderBy(order);

        List<Tuple> resultTupleList = entityManager
                .createQuery(orderCriteria)
                .setFirstResult(limitFrom)
                .setMaxResults(limitAmount)
                .getResultList();

        List<Order> resultOrderList = new ArrayList<>();

        resultTupleList.forEach(tuple -> {
            Order tupleOrder = (Order) tuple.get(0);
            Long countProducts = (Long) tuple.get(1);
            tupleOrder.setCountProducts(countProducts);
            resultOrderList.add(tupleOrder);
        });

        return resultOrderList;
    }

    private Predicate createSearchPredicate(String search, CriteriaBuilder criteriaBuilder, Root<Order> orderRoot, Join<Order, Customer> customerJoin) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.like(orderRoot.get(Order_.id).as(String.class), search));
        predicates.add(criteriaBuilder.like(customerJoin.get(Customer_.firstName).as(String.class), search));
        predicates.add(criteriaBuilder.like(customerJoin.get(Customer_.lastName).as(String.class), search));
        predicates.add(criteriaBuilder.like(customerJoin.get(Customer_.phoneNumber).as(String.class), search));

        return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        int limitAmount = request.getNumberOfElementsOnPage();
        int limitFrom = (request.getPageNumber() - 1) * limitAmount;
        String sortBy = request.getSortBy();
        Boolean isSortAsc = request.getIsSortAsc();
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> orderCriteria = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = orderCriteria.from(Order.class);

        Join<Order, Customer> customerJoin = orderRoot.join(Order_.customer);
        Predicate searchPredicate = createSearchPredicate(search, criteriaBuilder, orderRoot, customerJoin);
        javax.persistence.criteria.Order order = criteriaBuilder.asc(orderRoot.get("id"));

        if(sortBy.equals("customer")) {
            if(isSortAsc) {
                order = criteriaBuilder.asc(customerJoin.get(Customer_.firstName));
            } else {
                order = criteriaBuilder.desc(customerJoin.get(Customer_.firstName));
            }
        } else if (sortBy.equals("id")) {
            if(isSortAsc) {
                order = criteriaBuilder.asc(orderRoot.get(Order_.id));
            } else {
                order = criteriaBuilder.desc(orderRoot.get(Order_.id));
            }
        }

        orderCriteria
                .select(criteriaBuilder.count(orderRoot))
                .where(searchPredicate)
                .orderBy(order);

        Long count = entityManager
                .createQuery(orderCriteria)
                .setFirstResult(limitFrom)
                .setMaxResults(limitAmount)
                .getSingleResult();

        return count;
    }

    @Override
    public Long count() {
        return (long) entityManager
                .createQuery("select count(o) from Order o")
                .getSingleResult();
    }

}
