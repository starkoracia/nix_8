package ua.com.alevel.services.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.PaymentDao;
import ua.com.alevel.entities.Payment;
import ua.com.alevel.services.ServicePayment;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements ServicePayment {

    PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public Boolean create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public void update(Payment payment) {
        paymentDao.update(payment);
    }

    @Override
    public void delete(Payment payment) {
        paymentDao.delete(payment);
    }

    @Override
    public boolean existById(Long id) {
        return paymentDao.existById(id);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentDao.findById(id);
    }

    @Override
    public List<Payment> findAll() {
        return paymentDao.findAll();
    }

    @Override
    public Long count() {
        return paymentDao.count();
    }

}
