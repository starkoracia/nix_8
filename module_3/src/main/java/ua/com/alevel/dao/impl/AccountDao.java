package ua.com.alevel.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.entities.Account;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AccountDao implements BaseDao<Account> {

    private final SessionFactory sessionFactory;
    private Session session;

    public AccountDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean create(Account account) {
        getSession().save(account);
        if(account.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Account account) {
        getSession().update(account);
    }

    @Override
    public void delete(Account account) {
        getSession().delete(account);
    }

    @Override
    public boolean existById(Long id) {
        Query query = getSession()
                .createQuery("select count(ac) from Account ac where ac.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Account> findById(Long id) {
        Query query = getSession()
                .createQuery("select ac from Account ac where ac.id = :id")
                .setParameter("id", id);
        Account account = (Account) query.getSingleResult();
        return Optional.ofNullable(account);
    }

    @Override
    public List<Account> findAll() {
        List accountList = getSession()
                .createQuery("select ac from Account ac")
                .getResultList();
        return accountList;
    }

    @Override
    public Long count() {
        Long count = (Long) getSession()
                .createQuery("select count(ac) from Account ac")
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

    private void closeSession() {
        try {
            if (this.session.isOpen()) {
                this.session.close();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
