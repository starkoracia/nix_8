package ua.com.alevel.service.impl;

import ua.com.alevel.dao.impl.CategoryDao;
import ua.com.alevel.entities.Category;
import ua.com.alevel.service.ServiceCategory;

import java.util.List;
import java.util.Optional;

public class CategoryService implements ServiceCategory {

    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Boolean create(Category category) {
        return categoryDao.create(category);
    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }

    @Override
    public boolean existById(Long id) {
        return categoryDao.existById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Long count() {
        return categoryDao.count();
    }
}
