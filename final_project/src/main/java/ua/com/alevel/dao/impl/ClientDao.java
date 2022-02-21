package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoClient;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.entities.Client;
import ua.com.alevel.entities.Client_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ClientDao implements DaoClient {

    EntityManager entityManager;

    public ClientDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(Client client) {
        entityManager.persist(client);
        if (client.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Client client) {
        entityManager.merge(client);
    }

    @Override
    public void delete(Client client) {
        entityManager.remove(client);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(cl) from Client cl where cl.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Client> findById(Long id) {
        Query query = entityManager
                .createQuery("select cl from Client cl where cl.id = :id")
                .setParameter("id", id);
        Client client = (Client) query.getSingleResult();
        return Optional.ofNullable(client);
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientList = (List<Client>) entityManager
                .createQuery("select cl from Client cl")
                .getResultList();
        return clientList;
    }

    public List<Client> findAllFromRequest(PageDataRequest request) {
        int limitAmount = request.getNumberOfElementsOnPage();
        int limitFrom = (request.getPageNumber() - 1) * limitAmount;
        String sortBy = request.getSortBy();
        Boolean isSortAsc = request.getIsSortAsc();
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> clientCriteria = cb.createQuery(Client.class);
        Root<Client> clientRoot = clientCriteria.from(Client.class);

        Predicate searchPredicate = createSearchPredicate(search, cb, clientRoot);
        Order order = cb.asc(clientRoot.get("id"));

        if (sortBy.equals(Client_.ID) || sortBy.equals(Client_.NAME) || sortBy.equals(Client_.EMAIL)
                || sortBy.equals(Client_.MOBILE) || sortBy.equals(Client_.ANNOTATION)
                || sortBy.equals(Client_.RECOMMENDATION)) {
            if (isSortAsc) {
                order = cb.asc(clientRoot.get(sortBy));
            } else {
                order = cb.desc(clientRoot.get(sortBy));
            }
        }

        clientCriteria
                .select(clientRoot)
                .where(searchPredicate)
                .orderBy(order);

        List<Client> clientList = entityManager.createQuery(clientCriteria)
                .setFirstResult(limitFrom)
                .setMaxResults(limitAmount)
                .getResultList();

        return clientList;
    }

    public Long countNumberOfSearchMatches(PageDataRequest request) {
        String search = "%" + request.getSearchString() + "%";

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = cb.createQuery(Long.class);
        Root<Client> clientRoot = countCriteria.from(Client.class);

        Predicate searchPredicate = createSearchPredicate(search, cb, clientRoot);

        countCriteria
                .select(cb.count(clientRoot))
                .where(searchPredicate);

        Long count = entityManager
                .createQuery(countCriteria)
                .getSingleResult();

        return count;
    }

    private Predicate createSearchPredicate(String search, CriteriaBuilder cb, Root<Client> clientRoot) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(clientRoot.get(Client_.id).as(String.class), search));
        predicates.add(cb.like(clientRoot.get(Client_.name).as(String.class), search));
        predicates.add(cb.like(clientRoot.get(Client_.email).as(String.class), search));
        predicates.add(cb.like(clientRoot.get(Client_.mobile).as(String.class), search));
        predicates.add(cb.like(clientRoot.get(Client_.annotation).as(String.class), search));
        predicates.add(cb.like(clientRoot.get(Client_.recommendation).as(String.class), search));
        return cb.or(predicates.toArray(Predicate[]::new));
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(cl) from Client cl")
                .getSingleResult();
    }

    public Client getLastCreatedClient() {
        Client client = (Client) entityManager
                .createQuery("select c from Client c where " +
                        "c.id=(select max(c.id) from Client c)")
                .getSingleResult();
        return client;
    }

}
