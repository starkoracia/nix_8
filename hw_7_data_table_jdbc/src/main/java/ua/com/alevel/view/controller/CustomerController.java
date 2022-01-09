package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.persistence.dao.impl.CustomerDao;
import ua.com.alevel.persistence.entity.Customer;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    CustomerDao customerDao;

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping
    public String testCustomerCreate(Model model) {
        Customer customer = new Customer("Peter", "Parker", "380984252103");
        customerDao.create(customer);

        model.addAttribute("customer", customer);

        return "customers";
    }
}
