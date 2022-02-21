package ua.com.alevel.services.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.PaymentItemDao;
import ua.com.alevel.entities.PaymentItem;
import ua.com.alevel.services.ServicePaymentItem;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentItemService implements ServicePaymentItem {

    PaymentItemDao itemDao;

    public PaymentItemService(PaymentItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public Boolean create(PaymentItem item) {
        return itemDao.create(item);
    }

    @Override
    public void update(PaymentItem item) {
        itemDao.update(item);
    }

    @Override
    public void delete(PaymentItem item) {
        itemDao.delete(item);
    }

    @Override
    public boolean existById(Long id) {
        return itemDao.existById(id);
    }

    @Override
    public Optional<PaymentItem> findById(Long id) {
        return itemDao.findById(id);
    }

    @Override
    public List<PaymentItem> findAll() {
        return itemDao.findAll();
    }

    @Override
    public Long count() {
        return itemDao.count();
    }

    public PaymentItem getLastCreatedItem() {
        return itemDao.getLastCreatedItem();
    }

}
