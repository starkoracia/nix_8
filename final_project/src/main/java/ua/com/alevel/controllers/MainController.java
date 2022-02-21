package ua.com.alevel.controllers;

import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.EmployeeDto;
import ua.com.alevel.dto.entities.PaymentDto;
import ua.com.alevel.dto.entities.PaymentItemDto;
import ua.com.alevel.facade.impl.EmployeeFacade;
import ua.com.alevel.facade.impl.PaymentFacade;
import ua.com.alevel.facade.impl.PaymentItemFacade;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

   PaymentFacade paymentFacade;
   PaymentItemFacade itemFacade;
   EmployeeFacade employeeFacade;

    public MainController(PaymentFacade paymentFacade, PaymentItemFacade itemFacade, EmployeeFacade employeeFacade) {
        this.paymentFacade = paymentFacade;
        this.itemFacade = itemFacade;
        this.employeeFacade = employeeFacade;
    }

    @GetMapping("/payments")
    public List<PaymentDto> payments() {
        return paymentFacade.findAll();
    }

    @PostMapping("/payments")
    public PageDataResponse<PaymentDto> paymentsFromRequest(@RequestBody PageDataRequest request) {
        return paymentFacade.findAllFromRequest(request);
    }

    @PostMapping("/payments/matches")
    public Long searchMatches(@RequestBody PageDataRequest request) {
        return paymentFacade.countNumberOfSearchMatches(request);
    }


    @PostMapping("/payments/create")
    public Boolean employees(@RequestBody PaymentDto paymentDto) {
        return paymentFacade.create(paymentDto);
    }


    @GetMapping("/payments/balance")
    public BigDecimal getBalance() {
        return paymentFacade.getBalanceFromLastPayment();
    }

    @GetMapping("/employees")
    public List<EmployeeDto> employees() {
        return employeeFacade.findAll();
    }

    @GetMapping("/payments/items")
    public List<PaymentItemDto> getPaymentItems() {
        return itemFacade.findAll();
    }

    @GetMapping("/payments/items/last")
    public PaymentItemDto getLastItem() {
        return itemFacade.getLastCreatedItem();
    }

    @PostMapping("/payments/items/create")
    public Boolean createItem(@RequestBody PaymentItemDto itemDto) {
        return itemFacade.create(itemDto);
    }

}
