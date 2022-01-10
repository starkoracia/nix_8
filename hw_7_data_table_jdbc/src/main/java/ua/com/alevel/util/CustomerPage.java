package ua.com.alevel.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.alevel.persistence.entity.Customer;
import ua.com.alevel.persistence.entity.Product;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerPage extends Page<Customer> {

    public CustomerPage(List<Customer> elements) {
        super(elements);
    }

    public void sort(String sortField, boolean asc) {

        if (sortField.equals("fullName")) {
            if(asc) {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Customer::getFullName))
                        .toList();
            } else {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Customer::getFullName).reversed())
                        .toList();
            }
        }
        if (sortField.equals("phoneNumber")) {
            if(asc) {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Customer::getPhoneNumber))
                        .toList();
            } else {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Customer::getPhoneNumber).reversed())
                        .toList();
            }
        }
    }

}
