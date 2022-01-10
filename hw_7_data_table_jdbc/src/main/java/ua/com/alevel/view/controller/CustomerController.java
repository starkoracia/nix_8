package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.persistence.dao.impl.CustomerDao;
import ua.com.alevel.persistence.entity.Customer;
import ua.com.alevel.persistence.entity.Product;
import ua.com.alevel.util.CustomerPage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    CustomerDao customerDao;
    String errorMessage = "error";

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping
    public String getCustomers(Model model, @RequestParam Map<String, String> allRequestParams) {
        prepareCustomersModel(model, allRequestParams);
        return "customers";
    }

    @PostMapping
    public String getCustomersUpdateButton(Model model, @RequestParam Map<String, String> allRequestParams,
                                           @ModelAttribute("number_of_rows") int numberOfRows) {
        prepareCustomersModel(model, allRequestParams);
        return "customers";
    }

    @PostMapping("/create_customer")
    public String createCustomer(@ModelAttribute Customer customer, Model model,
                                @RequestParam Map<String, String> allRequestParams) {
        if (customer.getFirstName().length() > 0 && customer.getPhoneNumber() != null
                && customer.getPhoneNumber().length() > 0) {

            customerDao.create(customer);
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }
        prepareCustomersModel(model, allRequestParams);
        return "customers";
    }

    @PostMapping("/delete_customer/{id}")
    public String deleteCustomer(@PathVariable Long id,
                                 Model model, @RequestParam Map<String, String> allRequestParams) {
        Customer customer = new Customer();
        customer.setId(id);
        customerDao.delete(customer);

        prepareCustomersModel(model, allRequestParams);
        return "customers";
    }

    private void prepareCustomersModel(Model model, Map<String, String> allRequestParams) {
        List<Customer> customers = customerDao.findAll();
        CustomerPage customerPage = new CustomerPage(customers);

        String numberOfRows = allRequestParams.get("number_of_rows");
        if (numberOfRows != null) {
            try {
                customerPage.setNumberOfRows(Integer.parseInt(numberOfRows));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String pageNumber = allRequestParams.get("page_number");
        if (pageNumber != null) {
            try {
                customerPage.setPageNumber(Integer.parseInt(pageNumber));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String sorting = allRequestParams.get("sorting");
        String asc = allRequestParams.get("asc");
        boolean ascBool;
        if (sorting != null) {
            if (asc != null) {
                ascBool = Boolean.parseBoolean(asc);
            } else {
                ascBool = true;
            }
            customerPage.sort(sorting, ascBool);
            model.addAttribute("asc", ascBool);
            model.addAttribute("sorting", sorting);
        }

        model.addAttribute("customer", new Customer());
        model.addAttribute("totalRows", customerPage.getTotalRows());
        model.addAttribute("customerPage", customerPage);
        model.addAttribute("number_of_rows", customerPage.getNumberOfRows());
    }

}
