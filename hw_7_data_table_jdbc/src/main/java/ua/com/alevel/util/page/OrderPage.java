package ua.com.alevel.util.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.alevel.persistence.entity.Order;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderPage extends Page<Order> {

    public OrderPage(List<Order> elements) {
        super(elements);
    }

    @Override
    public void sort(String sortField, boolean asc) {
        if (sortField.equals("customerName")) {
            if (asc) {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Order::getCustomerFullName))
                        .toList();
            } else {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Order::getCustomerFullName).reversed())
                        .toList();
            }
        }
    }

}
