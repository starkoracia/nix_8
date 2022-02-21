package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoPaymentItem;
import ua.com.alevel.entities.PaymentItem;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PaymentItemDao implements DaoPaymentItem {

    EntityManager entityManager;

    public PaymentItemDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(PaymentItem paymentItem) {
        entityManager.persist(paymentItem);
        if(paymentItem.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(PaymentItem paymentItem) {
        entityManager.merge(paymentItem);
    }

    @Override
    public void delete(PaymentItem paymentItem) {
        entityManager.remove(paymentItem);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(pi) from PaymentItem pi where pi.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<PaymentItem> findById(Long id) {
        Query query = entityManager
                .createQuery("select pi from PaymentItem pi where pi.id = :id")
                .setParameter("id", id);
        PaymentItem paymentItem = (PaymentItem) query.getSingleResult();
        return Optional.ofNullable(paymentItem);
    }

    @Override
    public List<PaymentItem> findAll() {
        List<PaymentItem> paymentItemList = (List<PaymentItem>) entityManager
                .createQuery("select pi from PaymentItem pi")
                .getResultList();
        return paymentItemList;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(pi) from PaymentItem pi")
                .getSingleResult();
    }

    public PaymentItem getLastCreatedItem() {
        PaymentItem item = (PaymentItem) entityManager.createQuery("select i from PaymentItem i where" +
                        " i.id=(select max(i.id) from PaymentItem i)")
                .getSingleResult();
        return item;
    }

}
