package ua.com.alevel.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "job_and_materials")
@Getter
@Setter
@ToString
public class JobAndMaterial extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "warranty_days")
    private Integer warrantyDays;

    @Column(name = "zero_cost")
    private String zeroCost;

    @Column(name = "discount")
    private String discount;

    @ManyToOne
    @JoinColumn(name = "doer")
    private Employee doer;

    @Column(name = "comment")
    private String comment;

    @Column(name = "number_of")
    private Integer numberOf;

    @Column(name = "is_job")
    private Boolean isJob;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_material_id")
    private ProductMaterial productMaterial;


}