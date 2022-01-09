package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.persistence.dao.impl.ProductDao;
import ua.com.alevel.persistence.entity.Product;
import ua.com.alevel.util.ProductPage;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    ProductDao productDao;
    String errorMessage = "Заполните имя продукта";

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String getProducts(Model model, @RequestParam Map<String, String> allRequestParams) {
        prepareProductsModel(model, allRequestParams);
        return "products";
    }

    @PostMapping
    public String getProductsUpdate(Model model, @RequestParam Map<String, String> allRequestParams,
                                    @ModelAttribute("number_of_rows") int numberOfRows) {
        prepareProductsModel(model, allRequestParams);
        return "products";
    }

    @PostMapping("/create_product")
    public String createProduct(@ModelAttribute Product product, Model model,
                                @RequestParam Map<String, String> allRequestParams) {
        if (product.getProductName().length() > 0) {
            if (product.getPrice() == null) {
                product.setPrice(new BigDecimal("0.00"));
            }
            productDao.create(product);
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }
        prepareProductsModel(model, allRequestParams);
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

    private void prepareProductsModel(Model model, Map<String, String> allRequestParams) {
        List<Product> products = productDao.findAll();
        ProductPage productPage = new ProductPage(products);
        productPage.setElements(products);

        String numberOfRows = allRequestParams.get("number_of_rows");
        if(numberOfRows != null) {
            try {
                productPage.setNumberOfRows(Integer.parseInt(numberOfRows));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String pageNumber = allRequestParams.get("page_number");
        if(pageNumber != null) {
            try {
                productPage.setPageNumber(Integer.parseInt(pageNumber));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String sorting = allRequestParams.get("sorting");
        String asc = allRequestParams.get("asc");
        boolean ascBool;
        if(sorting != null) {
            if(asc != null) {
                ascBool = Boolean.parseBoolean(asc);
            } else {
                ascBool = true;
            }
            productPage.sort(sorting, ascBool);
            model.addAttribute("asc", ascBool);
            model.addAttribute("sorting", sorting);
        }

        model.addAttribute("product", new Product());
        model.addAttribute("totalRows", productPage.getTotalRows());
        model.addAttribute("productPage", productPage);
        model.addAttribute("number_of_rows", productPage.getNumberOfRows());
    }

}
