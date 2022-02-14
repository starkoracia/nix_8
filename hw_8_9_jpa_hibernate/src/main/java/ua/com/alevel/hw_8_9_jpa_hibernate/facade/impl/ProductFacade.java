package ua.com.alevel.hw_8_9_jpa_hibernate.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataResponse;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.ProductDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Product;
import ua.com.alevel.hw_8_9_jpa_hibernate.facade.BaseFacade;
import ua.com.alevel.hw_8_9_jpa_hibernate.services.impl.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductFacade implements BaseFacade<ProductDto> {

    ProductService productService;

    public ProductFacade(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Boolean create(ProductDto productDto) {
        return productService.create(productDto.convertToProduct());
    }

    @Override
    public void update(ProductDto productDto) {
        productService.update(productDto.convertToProduct());
    }

    @Override
    public void delete(ProductDto productDto) {
        productService.delete(productDto.convertToProduct());
    }

    @Override
    public boolean existById(Long id) {
        return productService.existById(id);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        Optional<Product> byId = productService.findById(id);
        if(byId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ProductDto.convertToDto(byId.get()));
    }

    @Override
    public List<ProductDto> findAll() {
        return productService.findAll()
                .stream()
                .map(ProductDto::convertToDto)
                .toList();
    }

    @Override
    public PageDataResponse<ProductDto> findAllFromRequest(PageDataRequest request) {
        List<Product> productList = productService.findAllFromRequest(request);
        PageDataResponse<ProductDto> productPageDataResponse = new PageDataResponse<>();
        List<ProductDto> productDtoList = productList.stream().map(ProductDto::convertToDto).toList();
        productPageDataResponse.setDtoEntities(productDtoList);
        if(request.getSearchString().equals("")) {
            productPageDataResponse.setAmountOfElements(count().intValue());
        } else {
            productPageDataResponse.setAmountOfElements(productList.size());
        }
        return productPageDataResponse;
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return productService.countNumberOfSearchMatches(request);
    }

    @Override
    public Long count() {
        return productService.count();
    }
}
