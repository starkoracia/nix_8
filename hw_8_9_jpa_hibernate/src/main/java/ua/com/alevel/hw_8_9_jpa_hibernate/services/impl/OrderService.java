package ua.com.alevel.hw_8_9_jpa_hibernate.services.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl.OrderDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Order;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;
import ua.com.alevel.hw_8_9_jpa_hibernate.services.BaseService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements BaseService<Order> {

    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Boolean create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    public boolean existById(Long id) {
        return orderDao.existById(id);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public List<Order> findAllFromRequest(PageDataRequest request) {
        return orderDao.findAllFromRequest(request);
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return orderDao.countNumberOfSearchMatches(request);
    }

    @Override
    public Long count() {
        return orderDao.count();
    }

    public List<Product> findProductsFromOrder(Order order) {
        return orderDao.findProductsFromOrder(order);
    }

    public Long countProductsFromOrder(Order order) {
        return orderDao.countProductsFromOrder(order);
    }
}
