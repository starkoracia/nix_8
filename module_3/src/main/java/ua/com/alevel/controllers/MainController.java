package ua.com.alevel.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dao.impl.AccountDao;
import ua.com.alevel.dao.impl.CategoryDao;
import ua.com.alevel.dao.impl.CustomerDao;
import ua.com.alevel.dao.impl.TransactionDao;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.AccountDto;
import ua.com.alevel.dto.entities.CustomerDto;
import ua.com.alevel.dto.entities.TransactionDto;
import ua.com.alevel.entities.Account;
import ua.com.alevel.entities.Category;
import ua.com.alevel.entities.Transaction;
import ua.com.alevel.facade.impl.CustomerFacade;
import ua.com.alevel.service.util.TransactionsCsvUtil;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping
public class MainController {

    private final TransactionDao transactionDao;
    private final CustomerDao customerDao;
    private final AccountDao accountDao;
    private final CategoryDao categoryDao;
    private final CustomerFacade customerFacade;

    public MainController(TransactionDao transactionDao, CustomerDao customerDao, AccountDao accountDao, CategoryDao categoryDao, CustomerFacade customerFacade) {
        this.transactionDao = transactionDao;
        this.customerDao = customerDao;
        this.accountDao = accountDao;
        this.categoryDao = categoryDao;
        this.customerFacade = customerFacade;
    }

    @PostMapping("/customers")
    public PageDataResponse<CustomerDto> getCustomers(@RequestBody PageDataRequest request) {
        PageDataResponse<CustomerDto> response = customerFacade.findAllFromRequest(request);
        return response;
    }

    @GetMapping("/customers")
    public List<CustomerDto> getCustomers() {
        List<CustomerDto> response = customerFacade.findAll();
        return response;
    }

    @PostMapping("/customers/coincidences")
    public Long getCountSearchMatches(@RequestBody PageDataRequest request) {
        Long count = customerFacade.countNumberOfSearchMatches(request);
        System.out.println("request = " + request);
        System.out.println("count = " + count);
        return count;
    }

    @PostMapping("/customers/accounts")
    public List<AccountDto> getAccountsFromCustomer(@RequestBody CustomerDto customerDto) {
        List<AccountDto> accounts = customerFacade.getAccountsFromCustomer(customerDto.convertToCustomer());
        return accounts;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return customerFacade.getCategories();
    }

    @PostMapping("/transactions/create")
    public Boolean createTransaction(@RequestBody TransactionDto transactionDto) {
        customerFacade.createTransaction(transactionDto);
        System.out.println("transactionDto = " + transactionDto);
        return true;
    }

    @PostMapping("/transactions/csv")
    public String getFile(@RequestBody AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getId());
        List<Transaction> transactions = customerFacade.getTransactionsFromAccount(account);
        String csvString = TransactionsCsvUtil.getCsvStringFromTransactions(transactions);
        return csvString;
    }


}
