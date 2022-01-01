package ua.com.alevel.persistence.dao.impl;

import ua.com.alevel.persistence.dao.DaoProduct;
import ua.com.alevel.persistence.entity.Product;

import java.util.List;

public class ProductDao implements DaoProduct {
    @Override
    public void create(Product entity) {

    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(Product entity) {

    }

    @Override
    public Product findById(Long aLong) {
        return null;
    }

    @Override
    public boolean existById(Long aLong) {
        return false;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
