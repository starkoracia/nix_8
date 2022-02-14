package ua.com.alevel.hw_8_9_jpa_hibernate.services.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl.CustomerDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Customer;
import ua.com.alevel.hw_8_9_jpa_hibernate.services.BaseService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements BaseService<Customer> {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Boolean create(Customer customer) {
        return customerDao.create(customer);
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);
    }

    @Override
    public boolean existById(Long id) {
        return customerDao.existById(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public List<Customer> findAllFromRequest(PageDataRequest request) {
        return customerDao.findAllFromRequest(request);
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return customerDao.countNumberOfSearchMatches(request);
    }


    @Override
    public Long count() {
        return customerDao.count();
    }

}
