package ua.com.alevel.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.dao.impl.AccountDao;
import ua.com.alevel.dao.impl.CategoryDao;
import ua.com.alevel.dao.impl.CustomerDao;
import ua.com.alevel.dao.impl.TransactionDao;
import ua.com.alevel.entities.Account;
import ua.com.alevel.entities.Category;
import ua.com.alevel.entities.Customer;
import ua.com.alevel.entities.Transaction;

import java.math.BigDecimal;
import java.util.Calendar;

@RestController
@RequestMapping
public class MainController {

    private final TransactionDao transactionDao;
    private final CustomerDao customerDao;
    private final AccountDao accountDao;
    private final CategoryDao categoryDao;

    public MainController(TransactionDao transactionDao, CustomerDao customerDao, AccountDao accountDao, CategoryDao categoryDao) {
        this.transactionDao = transactionDao;
        this.customerDao = customerDao;
        this.accountDao = accountDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping("test")
    public void test() {
        Category category = new Category();
        category.setIsIncome(true);
        category.setName("Доход");
        categoryDao.create(category);

        Customer customer = new Customer();
        customer.setName("Isaiev Gerald");
        customerDao.create(customer);
        System.out.println("customer = " + customer);

        Account account = new Account();
        account.setCustomer(customer);
        account.setBalance(new BigDecimal(0));
        accountDao.create(account);
        account.setName(customer.getId().toString() + "-" + account.getId().toString());
        accountDao.update(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCategory(categoryDao.findById(1L).get());
        transaction.setAmount(new BigDecimal(500));
        transaction.setAccountBalanceBefore(new BigDecimal(0));
        transaction.setAccountBalanceAfter(new BigDecimal(500));
        transaction.setDateTime(Calendar.getInstance());
        transactionDao.create(transaction);

        System.out.println("account = " + account);
        System.out.println("transaction = " + transaction);

    }
}
