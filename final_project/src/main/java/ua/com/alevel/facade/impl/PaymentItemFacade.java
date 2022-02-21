package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.PaymentItemDto;
import ua.com.alevel.entities.PaymentItem;
import ua.com.alevel.facade.FacadePaymentItem;
import ua.com.alevel.services.impl.PaymentItemService;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentItemFacade implements FacadePaymentItem {

    PaymentItemService itemService;

    public PaymentItemFacade(PaymentItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public Boolean create(PaymentItemDto dto) {
        return itemService.create(dto.toPaymentItem());
    }

    @Override
    public void update(PaymentItemDto dto) {
        itemService.update(dto.toPaymentItem());
    }

    @Override
    public void delete(PaymentItemDto dto) {
        itemService.delete(dto.toPaymentItem());
    }

    @Override
    public boolean existById(Long id) {
        return itemService.existById(id);
    }

    @Override
    public Optional<PaymentItemDto> findById(Long id) {
        PaymentItem item = itemService.findById(id).get();
        return Optional.ofNullable(PaymentItemDto.toDto(item));
    }

    @Override
    public List<PaymentItemDto> findAll() {
        return PaymentItemDto.toDtoList(itemService.findAll());
    }

    @Override
    public PageDataResponse<PaymentItemDto> findAllFromRequest(PageDataRequest request) {
        return null;
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return null;
    }

    @Override
    public Long count() {
        return itemService.count();
    }

    public PaymentItemDto getLastCreatedItem() {
        return PaymentItemDto.toDto(itemService.getLastCreatedItem());
    }

}
