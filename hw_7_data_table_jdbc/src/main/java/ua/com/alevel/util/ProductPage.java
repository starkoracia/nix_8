package ua.com.alevel.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.alevel.persistence.entity.Product;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductPage extends Page<Product> {

    public ProductPage(List<Product> elements) {
        super(elements);
    }

    public void sort(String sortField, boolean asc) {
        if (sortField.equals("productName")) {
            if(asc) {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Product::getProductName))
                        .toList();
            } else {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Product::getProductName).reversed())
                        .toList();
            }
        }
        if (sortField.equals("price")) {
            if(asc) {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Product::getPrice))
                        .toList();
            } else {
                this.elements = getElements().stream()
                        .sorted(Comparator.comparing(Product::getPrice).reversed())
                        .toList();
            }
        }
    }

}
