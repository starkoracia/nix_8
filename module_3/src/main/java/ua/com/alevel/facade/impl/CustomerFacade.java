package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.TransactionDao;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.AccountDto;
import ua.com.alevel.dto.entities.CustomerDto;
import ua.com.alevel.dto.entities.TransactionDto;
import ua.com.alevel.entities.Account;
import ua.com.alevel.entities.Category;
import ua.com.alevel.entities.Customer;
import ua.com.alevel.entities.Transaction;
import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.service.impl.AccountService;
import ua.com.alevel.service.impl.CustomerService;
import ua.com.alevel.service.impl.TransactionService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerFacade implements BaseFacade<CustomerDto> {

    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final AccountService accountService;

    public CustomerFacade(CustomerService customerService, TransactionService transactionService, AccountService accountService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @Override
    public Boolean create(CustomerDto dto) {
        return customerService.create(dto.convertToCustomer());
    }

    @Override
    public void update(CustomerDto dto) {
        customerService.update(dto.convertToCustomer());
    }

    @Override
    public void delete(CustomerDto dto) {
        customerService.delete(dto.convertToCustomer());
    }

    @Override
    public boolean existById(Long id) {
        return customerService.existById(id);
    }

    @Override
    public Optional<CustomerDto> findById(Long id) {
        Optional<Customer> byId = customerService.findById(id);
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(CustomerDto.convertToDto(byId.get()));
    }

    @Override
    public List<CustomerDto> findAll() {
        return CustomerDto.toDtoList(customerService.findAll());
    }

    @Override
    public PageDataResponse<CustomerDto> findAllFromRequest(PageDataRequest request) {
        PageDataResponse<CustomerDto> dataResponse = new PageDataResponse<>();
        List<CustomerDto> dtoList = CustomerDto.toDtoList(customerService.findAllFromRequest(request));
        dataResponse.setDtoEntities(dtoList);
        Integer count;
        if (request.getSearchString().equals("")) {
            count = count().intValue();
        } else {
            count = dtoList.size();
        }
        dataResponse.setAmountOfElements(count);
        return dataResponse;
    }

    public List<AccountDto> getAccountsFromCustomer(Customer customer) {
        List<Account> accounts = customerService.getAccountsFromCustomer(customer);
        return accounts.stream()
                .map(AccountDto::convertToDto)
                .toList();
    }

    public List<Category> getCategories() {
        return customerService.getCategories();
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return customerService.countNumberOfSearchMatches(request);
    }

    @Override
    public Long count() {
        return customerService.count();
    }

    public List<Transaction> getTransactionsFromAccount(Account account) {
        return transactionService.getTransactionsFromAccount(account);
    }

    public Boolean createTransaction(TransactionDto dto) {
        Account accountFrom = dto.getAccountFrom().convertToAccount();
        Account accountTo = dto.getAccountTo().convertToAccount();
        BigDecimal fromBalanceBefore = accountFrom.getBalance();
        BigDecimal toBalanceBefore = accountTo.getBalance();
        BigDecimal amount = dto.getAmount();

        BigDecimal fromBalanceAfter = fromBalanceBefore.subtract(amount);
        BigDecimal toBalanceAfter = toBalanceBefore.add(amount);


        Transaction transactionFrom = new Transaction();
        transactionFrom.setAccount(accountFrom);
        transactionFrom.setCategory(dto.getCategory());
        transactionFrom.setAmount(new BigDecimal("-" + amount));
        transactionFrom.setAccountBalanceBefore(fromBalanceBefore);
        transactionFrom.setAccountBalanceAfter(fromBalanceAfter);
        transactionFrom.setDateTime(Calendar.getInstance());

        transactionService.create(transactionFrom);

        accountFrom.setBalance(fromBalanceAfter);
        accountFrom.getTransactions().add(transactionFrom);
        accountService.update(accountFrom);

        Transaction transactionTo = new Transaction();
        transactionTo.setAccount(accountTo);
        transactionTo.setCategory(dto.getCategory());
        transactionTo.setAmount(amount);
        transactionTo.setAccountBalanceBefore(toBalanceBefore);
        transactionTo.setAccountBalanceAfter(toBalanceAfter);
        transactionTo.setDateTime(Calendar.getInstance());

        transactionService.create(transactionTo);

        accountTo.setBalance(toBalanceAfter);
        accountTo.getTransactions().add(transactionTo);
        accountService.update(accountTo);

        return true;
    }

}
