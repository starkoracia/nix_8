package ua.com.alevel.dao.impl;

import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.entities.Account;
import ua.com.alevel.entities.Category;
import ua.com.alevel.entities.Customer;
import ua.com.alevel.entities.Customer_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CustomerDao implements BaseDao<Customer> {

    private EntityManager entityManager;

    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(Customer customer) {
        entityManager.persist(customer);
        if(customer.getId() != null) {
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
                .createQuery("select count(cm) from Customer cm where cm.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Query query = entityManager
                .createQuery("select cm from Customer cm where cm.id = :id")
                .setParameter("id", id);
        Customer customer = (Customer) query.getSingleResult();
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAll() {
        List customerList = entityManager
                .createQuery("select cm from Customer cm")
                .getResultList();
        return customerList;
    }

    public List<Customer> findAllFromRequest(PageDataRequest request) {
        int limitAmount = request.getNumberOfElementsOnPage();
        int limitFrom = (request.getPageNumber() - 1) * limitAmount;
        String sortBy = request.getSortBy();
        Boolean isSortAsc = request.getIsSortAsc();
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> customerCriteria = cb.createQuery(Customer.class);
        Root<Customer> customerRoot = customerCriteria.from(Customer.class);

        Predicate searchPredicate = createSearchPredicate(search, cb, customerRoot);
        Order order = cb.asc(customerRoot.get("id"));
        if(sortBy.equals("id") || sortBy.equals("name")) {
            if(isSortAsc) {
                order = cb.asc(customerRoot.get(sortBy));
            } else {
                order = cb.desc(customerRoot.get(sortBy));
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

    public Long countNumberOfSearchMatches(PageDataRequest request) {
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = cb.createQuery(Long.class);
        Root<Customer> customerRoot = countCriteria.from(Customer.class);

        Predicate searchPredicate = createSearchPredicate(search,cb,customerRoot);

        countCriteria
                .select(cb.count(customerRoot))
                .where(searchPredicate);

        Long count = entityManager
                .createQuery(countCriteria)
                .getSingleResult();


        return count;
    }

    public List<Account> getAccountsFromCustomer(Customer customer) {
        Query query = entityManager.createQuery("select cm.accounts from Customer cm where cm.id = :id")
                .setParameter("id", customer.getId());
        List<Account> accountList = query.getResultList();
        return accountList;
    }

    public List<Category> getCategories() {
        Query query = entityManager.createQuery("select c from Category c");
        List<Category> categories = (List<Category>)query.getResultList();
        return categories;
    }

    @Override
    public Long count() {
        Long count = (Long) entityManager
                .createQuery("select count(cm) from Customer cm")
                .getSingleResult();
        return count;
    }

    private Predicate createSearchPredicate(String search, CriteriaBuilder cb, Root<Customer> customerRoot) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(customerRoot.get(Customer_.id).as(String.class), search));
        predicates.add(cb.like(customerRoot.get(Customer_.name).as(String.class), search));

        return cb.or(predicates.toArray(Predicate[]::new));
    }

}
