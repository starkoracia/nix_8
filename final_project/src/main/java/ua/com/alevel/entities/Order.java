package ua.com.alevel.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.entities.enums.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "model")
    private String model;

    @Column(name = "malfunction")
    private String malfunction;

    @Column(name = "appearance")
    private String appearance;

    @Column(name = "equipment")
    private String equipment;

    @Column(name = "acceptor_note")
    private String acceptorNote;

    @Column(name = "estimated_price")
    private String estimatedPrice;

    @Column(name = "quickly", nullable = false)
    private Boolean quickly;

    @Column(name = "deadline", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar deadline;

    @Column(name = "prepayment", nullable = false)
    private BigDecimal prepayment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "doer_id")
    private Employee doer;

    @Column(name = "doer_note")
    private String doerNote;

    @Column(name = "recommendation")
    private String recommendation;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "orders_job_and_materials",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "job_and_materials_id"))
    private Set<JobAndMaterial> jobAndMaterials = new LinkedHashSet<>();

}
