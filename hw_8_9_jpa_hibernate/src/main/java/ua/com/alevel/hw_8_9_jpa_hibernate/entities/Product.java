package ua.com.alevel.hw_8_9_jpa_hibernate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(nullable = false)
    private BigDecimal price;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Product(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrder(Order order) {
        order.getProducts().add(this);
        this.getOrders().add(order);
    }

}
