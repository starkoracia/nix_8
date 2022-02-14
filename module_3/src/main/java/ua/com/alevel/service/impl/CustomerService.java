package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.CustomerDao;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.entities.Account;
import ua.com.alevel.entities.Category;
import ua.com.alevel.entities.Customer;
import ua.com.alevel.service.ServiceCustomer;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ServiceCustomer {

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

    public List<Customer> findAllFromRequest(PageDataRequest request) {
        return customerDao.findAllFromRequest(request);
    }

    public List<Account> getAccountsFromCustomer(Customer customer) {
        return customerDao.getAccountsFromCustomer(customer);
    }

    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return customerDao.countNumberOfSearchMatches(request);
    }

    public List<Category> getCategories() {
        return customerDao.getCategories();
    }

    @Override
    public Long count() {
        return customerDao.count();
    }

}
