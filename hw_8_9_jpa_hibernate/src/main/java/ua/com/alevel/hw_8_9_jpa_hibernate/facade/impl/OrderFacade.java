package ua.com.alevel.hw_8_9_jpa_hibernate.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataResponse;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.OrderDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.ProductDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Order;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;
import ua.com.alevel.hw_8_9_jpa_hibernate.facade.BaseFacade;
import ua.com.alevel.hw_8_9_jpa_hibernate.services.impl.OrderService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderFacade implements BaseFacade<OrderDto> {

    private final OrderService orderService;

    public OrderFacade(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Boolean create(OrderDto dto) {
        return orderService.create(dto.convertToOrder());
    }

    @Override
    public void update(OrderDto dto) {
        orderService.update(dto.convertToOrder());
    }

    @Override
    public void delete(OrderDto dto) {
        orderService.delete(dto.convertToOrder());
    }

    @Override
    public boolean existById(Long id) {
        return orderService.existById(id);
    }

    @Override
    public Optional<OrderDto> findById(Long id) {
        Optional<Order> byId = orderService.findById(id);
        if(byId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(OrderDto.convertToDto(byId.get()));
    }

    @Override
    public List<OrderDto> findAll() {
        return orderService.findAll()
                .stream()
                .map(OrderDto::convertToDto)
                .toList();
    }

    @Override
    public PageDataResponse<OrderDto> findAllFromRequest(PageDataRequest request) {
        List<Order> orderList = orderService.findAllFromRequest(request);
        PageDataResponse<OrderDto> orderPageDataResponse = new PageDataResponse<>();
        List<OrderDto> orderDtoList = orderList.stream().map(OrderDto::convertToDto).toList();
        orderPageDataResponse.setDtoEntities(orderDtoList);
        if(request.getSearchString().equals("")) {
            orderPageDataResponse.setAmountOfElements(count().intValue());
        } else {
            orderPageDataResponse.setAmountOfElements(orderList.size());
        }
        return orderPageDataResponse;
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return orderService.countNumberOfSearchMatches(request);
    }

    @Override
    public Long count() {
        return orderService.count();
    }

    public List<ProductDto> findProductsFromOrder(OrderDto orderDto) {
        List<Product> productsFromOrder = orderService.findProductsFromOrder(orderDto.convertToOrder());
        return productsFromOrder
                .stream()
                .map(ProductDto::convertToDto)
                .toList();
    }

}
