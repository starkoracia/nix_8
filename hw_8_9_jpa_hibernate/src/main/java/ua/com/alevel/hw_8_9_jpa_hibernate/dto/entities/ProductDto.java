package ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductDto extends BaseDto<Product> {
    private Long id;
    private String productName;
    private BigDecimal price;

    public Product convertToProduct() {
        Product product = new Product();
        product.setId(this.id);
        product.setProductName(this.productName);
        product.setPrice(this.price);
        return product;
    }

    public static ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        return dto;
    }

}
