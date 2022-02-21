package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoPayment;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.entities.*;
import ua.com.alevel.entities.enums.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.persistence.criteria.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PaymentDao implements DaoPayment {

    EntityManager entityManager;

    public PaymentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(Payment payment) {
        entityManager.persist(payment);
        if (payment.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Payment payment) {
        entityManager.merge(payment);
    }

    @Override
    public void delete(Payment payment) {
        entityManager.remove(payment);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(pm) from Payment pm where pm.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        Query query = entityManager
                .createQuery("select pm from Payment pm where pm.id = :id")
                .setParameter("id", id);
        Payment payment = (Payment) query.getSingleResult();
        return Optional.ofNullable(payment);
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> paymentList = (List<Payment>) entityManager
                .createQuery("select pm from Payment pm")
                .getResultList();
        return paymentList;
    }

    public List<Payment> findAllFromRequest(PageDataRequest request) {
        int limitAmount = request.getNumberOfElementsOnPage();
        int limitFrom = (request.getPageNumber() - 1) * limitAmount;
        String sortBy = request.getSortBy();
        Boolean isSortAsc = request.getIsSortAsc();
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Payment> paymentCriteria = cb.createQuery(Payment.class);
        Root<Payment> paymentRoot = paymentCriteria.from(Payment.class);

        Join<Payment, PaymentItem> itemJoin = paymentRoot.join(Payment_.paymentItem);
        Join<Payment, Employee> cashierJoin = paymentRoot.join(Payment_.cashier);
        Join<Payment, Client> clientJoin = paymentRoot.join(Payment_.client, JoinType.LEFT);

        Predicate searchPredicate = createSearchPredicate(search, cb, paymentRoot, itemJoin,
                cashierJoin, clientJoin);
        Order order = cb.asc(paymentRoot.get("id"));

        if(sortBy.equals(Payment_.ID) || sortBy.equals(Payment_.DATE_TIME)
        || sortBy.equals(Payment_.PAYMENT_ITEM) || sortBy.equals(Payment_.AMOUNT)
        || sortBy.equals(Payment_.BALANCE_AFTER)) {
            if(isSortAsc) {
                order = cb.asc(paymentRoot.get(sortBy));
            } else {
                order = cb.desc(paymentRoot.get(sortBy));
            }
        } else if (sortBy.equals(Payment_.CLIENT)) {
            if(isSortAsc) {
                order = cb.asc(clientJoin.get(Client_.name));
            } else {
                order = cb.desc(clientJoin.get(Client_.name));
            }
        }

        paymentCriteria
                .select(paymentRoot)
                .where(searchPredicate)
                .orderBy(order);

        List<Payment> paymentList = entityManager.createQuery(paymentCriteria)
                .setFirstResult(limitFrom)
                .setMaxResults(limitAmount)
                .getResultList();

        return paymentList;
    }

    private Predicate createSearchPredicate(
            String search, CriteriaBuilder cb, Root<Payment> paymentRoot,
            Join<Payment, PaymentItem> itemJoin, Join<Payment, Employee> cashierJoin,
            Join<Payment, Client> clientJoin
    ) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(paymentRoot.get(Payment_.id).as(String.class), search));
        predicates.add(cb.like(itemJoin.get(PaymentItem_.name).as(String.class), search));
        predicates.add(cb.like(paymentRoot.get(Payment_.amount).as(String.class), search));
        predicates.add(cb.like(paymentRoot.get(Payment_.balanceAfter).as(String.class), search));
        predicates.add(cb.like(paymentRoot.get(Payment_.comment).as(String.class), search));
        predicates.add(cb.like(paymentRoot.get(Payment_.dateTime).as(String.class), search));
        predicates.add(cb.like(cashierJoin.get(Employee_.name).as(String.class), search));
        predicates.add(cb.like(clientJoin.get(Client_.name).as(String.class), search));
        return cb.or(predicates.toArray(Predicate[]::new));
    }

    public Long countNumberOfSearchMatches(PageDataRequest request) {
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = cb.createQuery(Long.class);
        Root<Payment> paymentRoot = countCriteria.from(Payment.class);

        Join<Payment, PaymentItem> itemJoin = paymentRoot.join(Payment_.paymentItem);
        Join<Payment, Employee> cashierJoin = paymentRoot.join(Payment_.cashier);
        Join<Payment, Client> clientJoin = paymentRoot.join(Payment_.client, JoinType.LEFT);

        Predicate searchPredicate = createSearchPredicate(search, cb, paymentRoot, itemJoin,
                cashierJoin, clientJoin);

        countCriteria
                .select(cb.count(paymentRoot))
                .where(searchPredicate);

        Long count = entityManager
                .createQuery(countCriteria)
                .getSingleResult();

        return count;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(pm) from Payment pm")
                .getSingleResult();
    }

    public BigDecimal getBalanceFromLastPayment() {
        BigDecimal balance = (BigDecimal) entityManager
                .createQuery("select p.balanceAfter from Payment p where " +
                        "p.id=(select max(p.id) from Payment p)")
                .getSingleResult();
        return balance;
    }

}
