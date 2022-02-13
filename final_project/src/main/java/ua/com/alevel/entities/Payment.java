package ua.com.alevel.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Table(name = "payments")
@Entity
@Getter
@Setter
@ToString
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_item_id")
    private PaymentItem paymentItem;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "income")
    private Boolean income;

    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTime;

    @Column(name = "balance_before")
    private BigDecimal balanceBefore;

    @Column(name = "balance_after")
    private BigDecimal balanceAfter;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee cashier;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
