package ua.com.alevel.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "relocatable_products")
@Entity
@Getter
@Setter
@ToString
public class RelocatableProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_material_id")
    private ProductMaterial productMaterial;

    @Column(name = "number_of")
    private Integer numberOf;

}
