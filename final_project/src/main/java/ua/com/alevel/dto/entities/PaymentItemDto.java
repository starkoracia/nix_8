package ua.com.alevel.dto.entities;

import lombok.Data;
import ua.com.alevel.entities.PaymentItem;

import java.io.Serializable;
import java.util.List;

@Data
public class PaymentItemDto extends BaseDto<PaymentItem> {
    private Integer id;
    private String name;
    private Boolean income;

    public PaymentItem toPaymentItem() {
        PaymentItem item = new PaymentItem();
        item.setId(this.id);
        item.setIncome(this.income);
        item.setName(this.name);
        return item;
    }

    public static PaymentItemDto toDto(PaymentItem item) {
        if(item == null) {
            return null;
        }
        PaymentItemDto dto = new PaymentItemDto();
        dto.setId(item.getId());
        dto.setIncome(item.getIncome());
        dto.setName(item.getName());
        return dto;
    }

    public static List<PaymentItemDto> toDtoList(List<PaymentItem> items) {
        return items.stream().map(PaymentItemDto::toDto).toList();
    }

}
