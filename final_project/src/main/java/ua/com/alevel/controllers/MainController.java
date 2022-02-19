package ua.com.alevel.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.dto.entities.PaymentDto;
import ua.com.alevel.facade.impl.PaymentFacade;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

   PaymentFacade paymentFacade;

    public MainController(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    @GetMapping("/payments")
    public List<PaymentDto> payments() {
        return paymentFacade.findAll();
    }

}
