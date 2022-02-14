package ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Customer;

@Getter
@Setter
@ToString
public class CustomerDto extends BaseDto<Customer> {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Customer convertToCustomer() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setPhoneNumber(this.phoneNumber);
        return customer;
    }

    public static CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        return dto;
    }

}
