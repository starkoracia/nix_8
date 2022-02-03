package ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.BaseDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Order;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    @Override
    public List<Order> findAllFromRequest(PageDataRequest request) {
        return null;
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return null;
    }

    @Override
    public Long count() {
        return (long) entityManager
                .createQuery("select count(order) from Order order")
                .getSingleResult();
    }

}
