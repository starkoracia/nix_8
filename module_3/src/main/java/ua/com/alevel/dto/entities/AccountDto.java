package ua.com.alevel.dto.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.entities.Account;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class AccountDto extends BaseDto<Account> {
    private String name;
    private BigDecimal balance;
    private CustomerDto customerDto;

    public static AccountDto convertToDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setBalance(account.getBalance());
        dto.setCustomerDto(CustomerDto.convertToDto(account.getCustomer()));
        return dto;
    }

    public Account convertToAccount() {
        Account account = new Account();
        account.setId(this.getId());
        account.setName(this.getName());
        account.setBalance(this.getBalance());
        account.setCustomer(this.getCustomerDto().convertToCustomer());
        return account;
    }

}
