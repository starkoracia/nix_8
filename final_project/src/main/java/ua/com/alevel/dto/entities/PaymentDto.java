package ua.com.alevel.dto.entities;

import lombok.Data;
import ua.com.alevel.entities.Payment;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Data
public class PaymentDto extends BaseDto<Payment> {
    private Long id;
    private PaymentItemDto paymentItem;
    private BigDecimal amount;
    private Boolean income;
    private Calendar dateTime;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String comment;
    private EmployeeDto cashier;
    private ClientDto client;
    private OrderDto order;

    public Payment toPayment() {
        Payment payment = new Payment();
        payment.setId(this.id);
        payment.setPaymentItem(this.paymentItem.toPaymentItem());
        payment.setAmount(this.amount);
        payment.setIncome(this.income);
        payment.setDateTime(this.dateTime);
        payment.setBalanceBefore(this.balanceBefore);
        payment.setBalanceAfter(this.balanceAfter);
        payment.setComment(this.comment);
        payment.setCashier(this.cashier.toEmployee());
        payment.setClient(this.client != null ? this.client.toClient() : null);
        payment.setOrder(this.order != null ? this.order.toOrder() : null);
        return payment;
    }

    public static PaymentDto toDto(Payment payment) {
        if(payment == null) {
            return null;
        }
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setPaymentItem(PaymentItemDto.toDto(payment.getPaymentItem()));
        dto.setAmount(payment.getAmount());
        dto.setIncome(payment.getIncome());
        dto.setDateTime(payment.getDateTime());
        dto.setBalanceBefore(payment.getBalanceBefore());
        dto.setBalanceAfter(payment.getBalanceAfter());
        dto.setComment(payment.getComment());
        dto.setCashier(EmployeeDto.toDto(payment.getCashier()));
        dto.setClient(ClientDto.toDto(payment.getClient()));
        dto.setOrder(OrderDto.toDto(payment.getOrder()));
        return dto;
    }

    public static List<PaymentDto> toDtoList(List<Payment> payments) {
        return payments.stream().map(PaymentDto::toDto).toList();
    }

}
