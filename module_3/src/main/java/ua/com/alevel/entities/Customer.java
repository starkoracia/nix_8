package ua.com.alevel.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
@ToString(callSuper = true)
public class Customer extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();


}
