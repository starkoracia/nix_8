package ua.com.alevel.dto.entities;

import lombok.Data;
import ua.com.alevel.entities.Employee;
import ua.com.alevel.entities.enums.Position;

import java.io.Serializable;
import java.util.List;

@Data
public class EmployeeDto extends BaseDto<Employee> {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Position position;

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(this.id);
        employee.setName(this.name);
        employee.setEmail(this.email);
        employee.setMobile(this.mobile);
        employee.setPosition(this.position);
        return employee;
    }

    public static EmployeeDto toDto(Employee employee) {
        if(employee == null) {
            return null;
        }
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setMobile(employee.getMobile());
        dto.setPosition(employee.getPosition());
        return dto;
    }

    public static List<EmployeeDto> toDtoList(List<Employee> employees) {
        return employees.stream().map(EmployeeDto::toDto).toList();
    }

}
