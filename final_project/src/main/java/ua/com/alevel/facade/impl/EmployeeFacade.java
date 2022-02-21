package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.EmployeeDto;
import ua.com.alevel.entities.Employee;
import ua.com.alevel.facade.FacadeEmployee;
import ua.com.alevel.services.impl.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeFacade implements FacadeEmployee {

    EmployeeService employeeService;

    public EmployeeFacade(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Boolean create(EmployeeDto dto) {
        return employeeService.create(dto.toEmployee());
    }

    @Override
    public void update(EmployeeDto dto) {
        employeeService.update(dto.toEmployee());
    }

    @Override
    public void delete(EmployeeDto dto) {
        employeeService.delete(dto.toEmployee());
    }

    @Override
    public boolean existById(Long id) {
        return employeeService.existById(id);
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        Employee employee = employeeService.findById(id).get();
        return Optional.ofNullable(EmployeeDto.toDto(employee));
    }

    @Override
    public List<EmployeeDto> findAll() {
        return EmployeeDto.toDtoList(employeeService.findAll());
    }

    @Override
    public PageDataResponse<EmployeeDto> findAllFromRequest(PageDataRequest request) {
        return null;
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return null;
    }

    @Override
    public Long count() {
        return employeeService.count();
    }

}
