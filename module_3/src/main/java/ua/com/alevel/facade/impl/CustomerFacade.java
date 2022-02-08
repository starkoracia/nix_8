package ua.com.alevel.facade.impl;

import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.CustomerDto;
import ua.com.alevel.entities.Customer;
import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.service.impl.CustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerFacade implements BaseFacade<CustomerDto> {

    private final CustomerService customerService;

    public CustomerFacade(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Boolean create(CustomerDto dto) {
        return customerService.create(dto.convertToCustomer());
    }

    @Override
    public void update(CustomerDto dto) {
        customerService.update(dto.convertToCustomer());
    }

    @Override
    public void delete(CustomerDto dto) {
        customerService.delete(dto.convertToCustomer());
    }

    @Override
    public boolean existById(Long id) {
        return customerService.existById(id);
    }

    @Override
    public Optional<CustomerDto> findById(Long id) {
        Optional<Customer> byId = customerService.findById(id);
        if(byId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(CustomerDto.convertToDto(byId.get()));
    }

    @Override
    public List<CustomerDto> findAll() {
        return CustomerDto.toDtoList(customerService.findAll());
    }

    @Override
    public PageDataResponse<CustomerDto> findAllFromRequest(PageDataRequest request) {
        return null;
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return null;
    }

    @Override
    public Long count() {
        return customerService.count();
    }

}
