package com.api.crud.controller;

import com.api.crud.entity.Employee;
import com.api.crud.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Test
    void ShouldBeAWelcomeMessage() {
        String expected = "Bienvenido a la API Rest para empleados!";
        String result = employeeController.hello();
        assertEquals(expected, result);
    }

    @Test
    void ShouldBeListEmployee() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        //hace una llamada a 2 empleados y comprueba que se ha creado una lista con eso.
        when(employeeService.getEmployee()).thenReturn(Arrays.asList(employee1, employee2));
        List<Employee> result = employeeController.getAll();
        assertEquals(2, result.size());
        //verifica que el servicio se ha llamado una vez.
        verify(employeeService, times(1)).getEmployee();
    }

    @Test
    void getById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}