package ua.com.alevel.hw_8_9_jpa_hibernate.controllers;

import org.springframework.web.bind.annotation.*;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataResponse;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.OrderDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.ProductDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;
import ua.com.alevel.hw_8_9_jpa_hibernate.facade.impl.OrderFacade;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderFacade orderFacade;

    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping()
    public PageDataResponse<OrderDto> getOrders(@RequestBody PageDataRequest pageDataRequest) {
        return orderFacade.findAllFromRequest(pageDataRequest);
    }

    @PostMapping("/coincidences")
    public Long getSearchCoincidences(@RequestBody PageDataRequest pageDataRequest) {
        Long numberOfSearchMatches = orderFacade.countNumberOfSearchMatches(pageDataRequest);
        return numberOfSearchMatches;
    }

    @PostMapping("/create")
    public Boolean createNewOrder(@RequestBody OrderDto orderDto) {
        orderFacade.create(orderDto);
        return true;
    }

    @PostMapping("/edit")
    public Boolean updateOrder(@RequestBody OrderDto orderDto) {
        orderFacade.update(orderDto);
        return true;
    }

    @PostMapping("/delete")
    public Boolean deleteOrder(@RequestBody OrderDto orderDto) {
        orderFacade.delete(orderDto);
        return true;
    }

    @PostMapping("/products")
    public List<ProductDto> findProductsFromOrder(@RequestBody OrderDto orderDto) {
        return orderFacade.findProductsFromOrder(orderDto);
    }



}
