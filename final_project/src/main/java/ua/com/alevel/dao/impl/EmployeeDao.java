package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoEmployee;
import ua.com.alevel.entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EmployeeDao implements DaoEmployee {

    EntityManager entityManager;

    public EmployeeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean create(Employee employee) {
        entityManager.persist(employee);
        if(employee.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Employee employee) {
        entityManager.merge(employee);
    }

    @Override
    public void delete(Employee employee) {
        entityManager.remove(employee);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(empl) from Employee empl where empl.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        Query query = entityManager
                .createQuery("select empl from Employee empl where empl.id = :id")
                .setParameter("id", id);
        Employee employee = (Employee) query.getSingleResult();
        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employeeList = (List<Employee>) entityManager
                .createQuery("select empl from Employee empl")
                .getResultList();
        return employeeList;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(empl) from Employee empl")
                .getSingleResult();
    }

}
