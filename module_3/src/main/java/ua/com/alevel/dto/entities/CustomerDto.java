package ua.com.alevel.dto.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.entities.Account;
import ua.com.alevel.entities.Customer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class CustomerDto extends BaseDto<Customer> {

    private String name;
    private List<Account> accounts = new ArrayList<>();

    public Customer convertToCustomer() {
        Customer customer = new Customer();
        customer.setId(this.getId());
        customer.setName(this.getName());
        customer.setAccounts(this.getAccounts());
        return customer;
    }

    public static CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setAccounts(customer.getAccounts());
        return dto;
    }

    public static List<CustomerDto> toDtoList(List<Customer> customerList) {
        return customerList.stream()
                .map(CustomerDto::convertToDto)
                .toList();
    }

}
