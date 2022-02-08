package ua.com.alevel.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.entities.Customer;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CustomerDao implements BaseDao<Customer> {

    private final SessionFactory sessionFactory;
    private Session session;

    public CustomerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean create(Customer customer) {
        getSession().save(customer);
        if(customer.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Customer customer) {
        getSession().update(customer);
    }

    @Override
    public void delete(Customer customer) {
        getSession().delete(customer);
    }

    @Override
    public boolean existById(Long id) {
        Query query = getSession()
                .createQuery("select count(cm) from Customer cm where cm.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Query query = getSession()
                .createQuery("select cm from Customer cm where cm.id = :id")
                .setParameter("id", id);
        Customer customer = (Customer) query.getSingleResult();
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAll() {
        List customerList = getSession()
                .createQuery("select cm from Customer cm")
                .getResultList();
        return customerList;
    }

    @Override
    public Long count() {
        Long count = (Long) getSession()
                .createQuery("select count(cm) from Customer cm")
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
