package ua.com.alevel.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.entities.Account;
import ua.com.alevel.entities.Transaction;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public class TransactionDao implements BaseDao<Transaction> {

    private final SessionFactory sessionFactory;
    private Session session;
    DriverManagerDataSource dataSource;

    public TransactionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean create(Transaction transaction) {
        getSession().save(transaction);
        if(transaction.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Transaction transaction) {
        getSession().update(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        getSession().delete(transaction);
    }

    @Override
    public boolean existById(Long id) {
        Query query = getSession()
                .createQuery("select count(tr) from Transaction tr where tr.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        Query query = getSession()
                .createQuery("select tr from Transaction tr where tr.id = :id")
                .setParameter("id", id);
        Transaction transaction = (Transaction) query.getSingleResult();
        return Optional.ofNullable(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        List transactionList = getSession()
                .createQuery("select tr from Transaction tr")
                .getResultList();
        return transactionList;
    }

    public List<Transaction> getTransactionsFromAccount(Account account) {
        List<Transaction> transactionList = getSession().createQuery("select tr from Transaction tr where tr.account.id = :id")
                .setParameter("id", account.getId())
                .getResultList();
        return transactionList;
    }

    @Override
    public Long count() {
        Long count = (Long) getSession()
                .createQuery("select count(tr) from Transaction tr")
                .getSingleResult();
        return count;
    }

    private Session getSession() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        this.session = session;
        return session;
    }

}
