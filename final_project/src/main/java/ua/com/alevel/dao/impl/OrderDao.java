package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoOrder;
import ua.com.alevel.entities.Order;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDao implements DaoOrder {

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
                .createQuery("select count(o) from Order o where o.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Order> findById(Long id) {
        Query query = entityManager
                .createQuery("select o from Order o where o.id = :id")
                .setParameter("id", id);
        Order order = (Order) query.getSingleResult();
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orderList = (List<Order>) entityManager
                .createQuery("select o from Order o")
                .getResultList();
        return orderList;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(o) from Order o")
                .getSingleResult();
    }

}
