package ua.com.alevel.view.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.persistence.dao.impl.ProductDao;
import ua.com.alevel.persistence.entity.Product;

import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:3000")
public class RController {

    ProductDao productDao;

    public RController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productDao.findAll();
    }

}
