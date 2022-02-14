package ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.BaseDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Customer;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Customer_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CustomerDao implements BaseDao<Customer> {

    private final EntityManager entityManager;

    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(Customer customer) {
        entityManager.persist(customer);
        if (customer.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Customer customer) {
        entityManager.merge(customer);
    }

    @Override
    public void delete(Customer customer) {
        entityManager.remove(customer);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(customer) from Customer customer where customer.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Query query = entityManager
                .createQuery("select customer from Customer customer where customer.id = :id")
                .setParameter("id", id);
        Customer customer = (Customer) query.getSingleResult();
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = (List<Customer>) entityManager
                .createQuery("select customer from Customer customer")
                .getResultList();
        return customerList;
    }

    @Override
    public List<Customer> findAllFromRequest(PageDataRequest request) {
        int limitAmount = request.getNumberOfElementsOnPage();
        int limitFrom = (request.getPageNumber() - 1) * limitAmount;
        String sortBy = request.getSortBy();
        Boolean isSortAsc = request.getIsSortAsc();
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> customerCriteria = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> customerRoot = customerCriteria.from(Customer.class);

        Predicate searchPredicate = createSearchPredicate(search, criteriaBuilder, customerRoot);
        Order order = criteriaBuilder.asc(customerRoot.get("id"));

        if (sortBy.equals(Customer_.ID) || sortBy.equals(Customer_.FIRST_NAME)
                || sortBy.equals(Customer_.LAST_NAME) || sortBy.equals(Customer_.PHONE_NUMBER)) {
            if(isSortAsc) {
                order = criteriaBuilder.asc(customerRoot.get(sortBy));
            } else {
                order = criteriaBuilder.desc(customerRoot.get(sortBy));
            }
        }

        customerCriteria
                .select(customerRoot)
                .where(searchPredicate)
                .orderBy(order);

        List<Customer> customerList = entityManager
                .createQuery(customerCriteria)
                .setFirstResult(limitFrom)
                .setMaxResults(limitAmount)
                .getResultList();

        return customerList;
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        Root<Customer> customerRoot = countCriteria.from(Customer.class);

        Predicate searchPredicate = createSearchPredicate(search, criteriaBuilder, customerRoot);

        countCriteria
                .select(criteriaBuilder.count(customerRoot))
                .where(searchPredicate);

        Long count = entityManager
                .createQuery(countCriteria)
                .getSingleResult();

        return count;
    }

    @Override
    public Long count() {
        return (long) entityManager.createQuery("select count(customer) from Customer customer").getSingleResult();
    }

    private Predicate createSearchPredicate(String search, CriteriaBuilder criteriaBuilder, Root<Customer> customerRoot) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.like(customerRoot.get(Customer_.id).as(String.class), search));
        predicates.add(criteriaBuilder.like(customerRoot.get(Customer_.firstName).as(String.class), search));
        predicates.add(criteriaBuilder.like(customerRoot.get(Customer_.lastName).as(String.class), search));
        predicates.add(criteriaBuilder.like(customerRoot.get(Customer_.phoneNumber).as(String.class), search));

        return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
    }

}
