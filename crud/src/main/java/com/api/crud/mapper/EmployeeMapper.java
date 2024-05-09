package com.api.crud.mapper;

import com.api.crud.entity.Employee;
import com.api.crud.model.EmployeeDTO;
//import org.mapstruct.Mapper;
//
//@Mapper
public interface EmployeeMapper {
    EmployeeDTO employeeToEmployeeDTO(Employee employee);
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
