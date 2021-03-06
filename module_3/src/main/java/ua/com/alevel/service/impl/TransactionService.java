package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.TransactionDao;
import ua.com.alevel.entities.Account;
import ua.com.alevel.entities.Transaction;
import ua.com.alevel.service.ServiceTransaction;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ServiceTransaction {

    private final TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public Boolean create(Transaction transaction) {
        return transactionDao.create(transaction);
    }

    @Override
    public void update(Transaction transaction) {
        transactionDao.update(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionDao.delete(transaction);
    }

    @Override
    public boolean existById(Long id) {
        return transactionDao.existById(id);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionDao.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    public List<Transaction> getTransactionsFromAccount(Account account) {
       return transactionDao.getTransactionsFromAccount(account);
    }

    @Override
    public Long count() {
        return transactionDao.count();
    }
}
