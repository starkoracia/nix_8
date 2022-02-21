package ua.com.alevel.services.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.PaymentDao;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.ClientDto;
import ua.com.alevel.dto.entities.PaymentDto;
import ua.com.alevel.entities.Payment;
import ua.com.alevel.services.ServicePayment;

import java.math.BigDecimal;
import java.util.Calendar;
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
        BigDecimal balanceBefore = getBalanceFromLastPayment();
        BigDecimal balanceAfter;
        if(payment.getIncome()) {
            balanceAfter = balanceBefore.add(payment.getAmount());
        } else {
            balanceAfter = balanceBefore.subtract(payment.getAmount());
        }
        payment.setBalanceBefore(balanceBefore);
        payment.setBalanceAfter(balanceAfter);
        payment.setDateTime(Calendar.getInstance());

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

    public PageDataResponse<PaymentDto> findAllFromRequest(PageDataRequest request) {
        List<Payment> payments = paymentDao.findAllFromRequest(request);
        PageDataResponse<PaymentDto> dataResponse = new PageDataResponse<>();
        dataResponse.setDtoEntities(PaymentDto.toDtoList(payments));
        if(request.getSearchString().equals("")) {
            dataResponse.setAmountOfElements(count().intValue());
        } else {
            dataResponse.setAmountOfElements(payments.size());
        }
        return dataResponse;
    }

    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return paymentDao.countNumberOfSearchMatches(request);
    }

    @Override
    public Long count() {
        return paymentDao.count();
    }

    public BigDecimal getBalanceFromLastPayment() {
        return paymentDao.getBalanceFromLastPayment();
    }

}
