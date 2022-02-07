package ua.com.alevel.hw_8_9_jpa_hibernate.controllers;

import org.springframework.web.bind.annotation.*;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataResponse;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.ProductDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.facade.impl.ProductFacade;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping()
    public PageDataResponse<ProductDto> getProducts(@RequestBody PageDataRequest pageDataRequest) {
        return productFacade.findAllFromRequest(pageDataRequest);
    }

    @GetMapping()
    public List<ProductDto> getProducts() {
        return productFacade.findAll();
    }

    @PostMapping("/coincidences")
    public Long getSearchCoincidences(@RequestBody PageDataRequest pageDataRequest) {
        Long numberOfSearchMatches = productFacade.countNumberOfSearchMatches(pageDataRequest);
        return numberOfSearchMatches;
    }

    @PostMapping("/create")
    public Boolean createNewProduct(@RequestBody ProductDto productDto) {
        productFacade.create(productDto);
        return true;
    }

    @PostMapping("/edit")
    public Boolean updateProduct(@RequestBody ProductDto productDto) {
        productFacade.update(productDto);
        return true;
    }

    @PostMapping("/delete")
    public Boolean deleteProduct(@RequestBody ProductDto productDto) {
        productFacade.delete(productDto);
        return true;
    }

}
