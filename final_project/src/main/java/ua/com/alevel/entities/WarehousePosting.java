package ua.com.alevel.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "warehouse_posting")
@Getter
@Setter
@ToString
public class WarehousePosting extends WarehouseOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Client supplier;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "warehouse_write_off_product_materials",
            joinColumns = @JoinColumn(name = "warehouse_write_off_id"),
            inverseJoinColumns = @JoinColumn(name = "product_materials_id"))
    private Set<ProductMaterial> productMaterials = new LinkedHashSet<>();

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;

}
