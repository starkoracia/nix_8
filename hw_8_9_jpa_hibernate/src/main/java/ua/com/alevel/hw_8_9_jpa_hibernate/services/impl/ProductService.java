package ua.com.alevel.hw_8_9_jpa_hibernate.services.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl.ProductDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;
import ua.com.alevel.hw_8_9_jpa_hibernate.services.BaseService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements BaseService<Product> {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Boolean create(Product product) {
         return productDao.create(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Override
    public boolean existById(Long id) {
        return productDao.existById(id);

    }

    @Override
    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findAllFromRequest(PageDataRequest request) {
        return productDao.findAllFromRequest(request);
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return productDao.countNumberOfSearchMatches(request);
    }

    @Override
    public Long count() {
        return productDao.count();
    }

}
