package ua.com.alevel.hw_8_9_jpa_hibernate.controllers;

import org.springframework.web.bind.annotation.*;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl.CustomerDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Customer;

import java.util.List;

@RestController
@RequestMapping()
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

    private final CustomerDao customerDao;

    public MainController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping("/test")
    public String testCustomerDao() {
        
        PageDataRequest request = new PageDataRequest();
        request.setPageNumber(1);
        request.setIsSortAsc(false);
        request.setNumberOfElementsOnPage(5);
        request.setSearchString("98");
        request.setSortBy("id");


        List<Customer> allFromRequest = customerDao.findAllFromRequest(request);
        System.out.println("allFromRequest = " + allFromRequest);
        return "";
    }

}
