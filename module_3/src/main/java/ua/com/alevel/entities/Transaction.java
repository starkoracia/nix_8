package ua.com.alevel.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString(callSuper = true)
public class Transaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "date_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTime;

    @Column(name = "amount", precision = 16, scale = 2, updatable = false)
    private BigDecimal amount;

    @Column(name = "account_balance_before", precision = 16, scale = 2, updatable = false)
    private BigDecimal accountBalanceBefore;

    @Column(name = "account_balance_after", precision = 16, scale = 2, updatable = false)
    private BigDecimal accountBalanceAfter;

    @ManyToOne
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
