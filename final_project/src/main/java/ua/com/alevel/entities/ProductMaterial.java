package ua.com.alevel.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_materials")
@Getter
@Setter
@ToString
public class ProductMaterial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name = "is_warranty")
    private Boolean isWarranty;

    @Column(name = "zero_cost")
    private BigDecimal zeroCost;

    @Column(name = "repair_cost")
    private BigDecimal repairCost;

    @Column(name = "trade_cost")
    private BigDecimal tradeCost;

    @Column(name = "number_of")
    private Integer numberOf;

    @Column(name = "in_stock")
    private Boolean inStock;


}
