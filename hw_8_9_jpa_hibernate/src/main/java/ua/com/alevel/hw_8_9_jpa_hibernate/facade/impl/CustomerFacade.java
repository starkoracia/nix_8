package ua.com.alevel.hw_8_9_jpa_hibernate.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.PageDataResponse;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.CustomerDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.dto.entities.ProductDto;
import ua.com.alevel.hw_8_9_jpa_hibernate.entities.Customer;
import ua.com.alevel.hw_8_9_jpa_hibernate.facade.BaseFacade;
import ua.com.alevel.hw_8_9_jpa_hibernate.services.impl.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerFacade implements BaseFacade<CustomerDto> {

    private final CustomerService customerService;

    public CustomerFacade(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Boolean create(CustomerDto customerDto) {
        return customerService.create(customerDto.convertToCustomer());
    }

    @Override
    public void update(CustomerDto customerDto) {
        customerService.update(customerDto.convertToCustomer());
    }

    @Override
    public void delete(CustomerDto customerDto) {
        customerService.delete(customerDto.convertToCustomer());
    }

    @Override
    public boolean existById(Long id) {
        return customerService.existById(id);
    }

    @Override
    public Optional<CustomerDto> findById(Long id) {
        Optional<Customer> byId = customerService.findById(id);
        if(byId.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(CustomerDto.convertToDto(byId.get()));
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerService.findAll()
                .stream()
                .map(CustomerDto::convertToDto)
                .toList();
    }

    @Override
    public PageDataResponse<CustomerDto> findAllFromRequest(PageDataRequest request) {
        List<Customer> customerList = customerService.findAllFromRequest(request);
        PageDataResponse<CustomerDto> customerPageDataResponse = new PageDataResponse<>();
        List<CustomerDto> customerDtoList = customerList.stream().map(CustomerDto::convertToDto).toList();
        customerPageDataResponse.setDtoEntities(customerDtoList);
        if(request.getSearchString().equals("")) {
            customerPageDataResponse.setAmountOfElements(count().intValue());
        } else {
            customerPageDataResponse.setAmountOfElements(customerList.size());
        }
        return customerPageDataResponse;
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return customerService.countNumberOfSearchMatches(request);
    }

    @Override
    public Long count() {
        return customerService.count();
    }

}
