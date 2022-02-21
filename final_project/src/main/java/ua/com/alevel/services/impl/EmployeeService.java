package ua.com.alevel.services.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.EmployeeDao;
import ua.com.alevel.entities.Employee;
import ua.com.alevel.services.ServiceEmployee;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements ServiceEmployee {

    EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Boolean create(Employee employee) {
        return employeeDao.create(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }

    @Override
    public boolean existById(Long id) {
        return employeeDao.existById(id);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public Long count() {
        return employeeDao.count();
    }

}
