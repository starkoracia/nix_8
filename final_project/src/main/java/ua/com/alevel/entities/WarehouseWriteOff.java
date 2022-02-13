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
@Table(name = "warehouse_write_off")
@Getter
@Setter
@ToString
public class WarehouseWriteOff extends WarehouseOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTime;

}
