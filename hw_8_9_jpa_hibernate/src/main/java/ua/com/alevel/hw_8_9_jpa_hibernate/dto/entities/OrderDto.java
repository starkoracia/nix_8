package ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Customer;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Order;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class OrderDto extends BaseDto<Order> {
    private Long id;
    private Customer customer;
    private Set<Product> products = new LinkedHashSet<>();
    private Long countProducts;

    public Order convertToOrder() {
        Order order = new Order();
        order.setId(this.id);
        order.setCustomer(this.customer);
        order.setProducts(this.products);
        return order;
    }

    public static OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCustomer(order.getCustomer());
        dto.setCountProducts(order.getCountProducts());
        return dto;
    }

}
