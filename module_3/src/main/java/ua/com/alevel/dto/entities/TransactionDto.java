package ua.com.alevel.dto.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.entities.Category;
import ua.com.alevel.entities.Transaction;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class TransactionDto extends BaseDto<Transaction> {
    private AccountDto accountFrom;
    private AccountDto accountTo;
    private Category category;
    private BigDecimal amount;
}
