package ua.com.alevel.hw_8_9_jpa_hibernate.controllers;

import org.springframework.web.bind.annotation.*;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl.OrderDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl.ProductDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataResponse;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Order;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

    ProductDao productDao;
    OrderDao orderDao;

    public MainController(ProductDao productDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

    @PostMapping("/products")
    public PageDataResponse<Product> getProducts(@RequestBody PageDataRequest pageDataRequest) {

        Integer elementsOnPage = pageDataRequest.getNumberOfElementsOnPage();
        Integer pageNumber = pageDataRequest.getPageNumber();

        Product product = productDao.findById(1L).get();
        Order order = orderDao.findById(2L).get();

        PageDataRequest dataRequest = new PageDataRequest();
        dataRequest.setPageNumber(pageNumber);
        dataRequest.setNumberOfElementsOnPage(elementsOnPage);
        dataRequest.setIsSortAsc(false);
        dataRequest.setSortBy("price");
        dataRequest.setSearchString("1");
        System.out.println("dataRequest = " + dataRequest);
        List<Product> productsFromRequest = productDao.findAllFromRequest(dataRequest);

        PageDataResponse<Product> productPageDataResponse = new PageDataResponse<>();
        Long amountOfElements = productDao.count();
        productPageDataResponse.setEntities(productsFromRequest);
        productPageDataResponse.setAmountOfElements(amountOfElements);
        System.out.println("productPageDataResponse = " + productPageDataResponse);

        return productPageDataResponse;
    }

}
