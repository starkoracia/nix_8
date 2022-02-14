package ua.com.alevel.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ua.com.alevel.dao.DaoCategory;
import ua.com.alevel.entities.Category;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDao implements DaoCategory {

    private final SessionFactory sessionFactory;

    public CategoryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean create(Category category) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(category);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
        if (category.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Category category) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(category);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void delete(Category category) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(category);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public boolean existById(Long id) {
        Query query = getSession()
                .createQuery("select count(category) from Category category where category.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Category> findById(Long id) {
        Query query = getSession()
                .createQuery("select category from Category category where category.id = :id")
                .setParameter("id", id);
        Category category = (Category) query.getSingleResult();
        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> findAll() {
        List categoryList = getSession()
                .createQuery("select category from Category category")
                .getResultList();
        return categoryList;
    }

    @Override
    public Long count() {
        Long count = (Long) getSession()
                .createQuery("select count(category) from Category category")
                .getSingleResult();
        return count;
    }

    private Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    private void closeSession(Session session) {
        try {
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


}
