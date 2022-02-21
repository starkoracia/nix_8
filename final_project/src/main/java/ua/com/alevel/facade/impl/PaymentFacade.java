package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.PaymentDto;
import ua.com.alevel.entities.Payment;
import ua.com.alevel.facade.FacadePayment;
import ua.com.alevel.services.impl.PaymentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentFacade implements FacadePayment {

    PaymentService paymentService;

    public PaymentFacade(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Boolean create(PaymentDto dto) {
        return paymentService.create(dto.toPayment());
    }

    @Override
    public void update(PaymentDto dto) {
        paymentService.update(dto.toPayment());
    }

    @Override
    public void delete(PaymentDto dto) {
        paymentService.delete(dto.toPayment());
    }

    @Override
    public boolean existById(Long id) {
        return paymentService.existById(id);
    }

    @Override
    public Optional<PaymentDto> findById(Long id) {
        Payment payment = paymentService.findById(id).get();
        return Optional.ofNullable(PaymentDto.toDto(payment));
    }

    @Override
    public List<PaymentDto> findAll() {
        return PaymentDto.toDtoList(paymentService.findAll());
    }

    @Override
    public PageDataResponse<PaymentDto> findAllFromRequest(PageDataRequest request) {
        return paymentService.findAllFromRequest(request);
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return paymentService.countNumberOfSearchMatches(request);
    }

    @Override
    public Long count() {
        return paymentService.count();
    }

    public BigDecimal getBalanceFromLastPayment() {
        return paymentService.getBalanceFromLastPayment();
    }

}
