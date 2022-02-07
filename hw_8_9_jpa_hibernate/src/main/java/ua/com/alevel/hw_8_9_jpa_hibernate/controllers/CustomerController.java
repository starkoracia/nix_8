package ua.com.alevel.hw_8_9_jpa_hibernate.controllers;

import org.springframework.web.bind.annotation.*;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataResponse;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.CustomerDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.facade.impl.CustomerFacade;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerFacade customerFacade;

    public CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @PostMapping()
    public PageDataResponse<CustomerDto> getCustomers(@RequestBody PageDataRequest pageDataRequest) {
        return customerFacade.findAllFromRequest(pageDataRequest);
    }

    @PostMapping("/coincidences")
    public Long getSearchCoincidences(@RequestBody PageDataRequest pageDataRequest) {
        Long numberOfSearchMatches = customerFacade.countNumberOfSearchMatches(pageDataRequest);
        return numberOfSearchMatches;
    }

    @PostMapping("/create")
    public Boolean createNewCustomer(@RequestBody CustomerDto customerDto) {
        customerFacade.create(customerDto);
        return true;
    }

    @PostMapping("/edit")
    public Boolean updateCustomer(@RequestBody CustomerDto customerDto) {
        customerFacade.update(customerDto);
        return true;
    }

    @PostMapping("/delete")
    public Boolean deleteProduct(@RequestBody CustomerDto customerDto) {
        customerFacade.delete(customerDto);
        return true;
    }

    @GetMapping("/options")
    public List<CustomerDto> getAllCustomers() {
        return customerFacade.findAll();
    }

}
