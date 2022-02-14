package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.AccountDao;
import ua.com.alevel.entities.Account;
import ua.com.alevel.service.ServiceAccount;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements ServiceAccount {

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Boolean create(Account account) {
        return accountDao.create(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Account account) {
        accountDao.delete(account);
    }

    @Override
    public boolean existById(Long id) {
        return accountDao.existById(id);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Long count() {
        return accountDao.count();
    }
}
