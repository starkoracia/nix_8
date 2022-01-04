package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.persistence.dao.impl.ProductDao;
import ua.com.alevel.persistence.entity.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping("/")
public class MainTestController {

    ProductDao productDao;
    String errorMessage = "Заполните имя продукта";

    public MainTestController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        prepareProductsModel(model);
        return "products";
    }

    @PostMapping("/create_product")
    public String createProduct(@ModelAttribute Product product, Model model) {
        if (product.getProductName().length() > 0) {
            if (product.getPrice() == null) {
                product.setPrice(new BigDecimal("0.00"));
            }
            productDao.create(product);
            return "redirect:/products";
        }
        model.addAttribute("errorMessage", errorMessage);
        prepareProductsModel(model);
        return "products";
    }

    @PostMapping("/delete_product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product product = new Product();
        product.setId(id);
        productDao.delete(product);
        return "redirect:/products";
    }

    @GetMapping("/update_product/{id}")
    public String redirectToProductUpdatePage(@PathVariable Long id, Model model) {
        prepareUpdateProductModel(model, id);
        return "update_product";
    }

    @PostMapping("/update_product/{id}")
    public String updateProduct(HttpServletRequest request, Model model, @PathVariable Long id) {

        String productName = request.getParameter("productName");
        String priceString = request.getParameter("price");
        BigDecimal price;

        if (productName != null && productName.length() > 0) {
            if (priceString == null || priceString.isBlank()) {
                price = new BigDecimal("0.00");
            } else {
                price = new BigDecimal(priceString);
            }
            Product product = new Product(id, productName, price);
            productDao.update(product);
            return "redirect:/products";
        }
        model.addAttribute("errorMessage", errorMessage);
        prepareUpdateProductModel(model, id);

        return "update_product";
    }

    private void prepareUpdateProductModel(Model model, Long id) {
        Product productToUpdate = productDao.findById(id);
        model.addAttribute("productToUpdate", productToUpdate);
    }

    private void prepareProductsModel(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", productDao.findAll());
    }

}
