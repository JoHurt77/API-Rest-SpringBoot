package com.api.crud.controller;

import com.api.crud.entity.Department;
import com.api.crud.entity.Employee;
import com.api.crud.entity.WorkCenter;
import com.api.crud.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee1;

    @BeforeEach
    public void setUp() {
        // Inicializar un empleado que se utilizará en las pruebas
        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Juan");
        employee1.setLastName("Martin");
        employee1.setAddress("House");
        employee1.setEmail("juan@gmail.com");
        employee1.setWorkCenter(new WorkCenter());
        employee1.setDepartment(new Department());

        // Configurar el mock para devolver employee1 cuando se llama a getEmployeeById(1L)
        lenient().when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee1));

        // Configurar el mock para guardar employee1 y devolverlo
        lenient().when(employeeService.saveOrUpdate(employee1)).thenReturn(employee1);

        // Configurar el mock para realizar una operación de eliminación sin hacer nada
        lenient().doNothing().when(employeeService).deleteEmployee(employee1.getId());
    }

    @Test
    @DisplayName("Should return welcome message")
    public void testHello() {
        ResponseEntity<String> response = employeeController.hello();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bienvenido a la API Rest para empleados!", response.getBody());
    }

    @Test
    @DisplayName("Should return all employees")
    public void testGetAllEmployees() {
        // Crear una lista de empleados que incluye employee1
        List<Employee> employees = Collections.singletonList(employee1);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        verify(employeeService, times(1)).getAllEmployees();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
    }

    @Test
    @DisplayName("Should return an empty list")
    public void testGetAllEmployeesEmpty() {
        // Configurar el mock para devolver una lista vacía
        List<Employee> employees = new ArrayList<>();
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Llamar al método bajo prueba
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        // Verificar que el servicio fue llamado correctamente
        verify(employeeService, times(1)).getAllEmployees();

        // Verificar que el código de estado sea NO_CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verificar que el cuerpo de la respuesta sea null
        assertNull(response.getBody());
    }



    @Test
    @DisplayName("Should return an employee by ID")
    public void testGetEmployeeById() {
        Long id = 1L;

        ResponseEntity<Employee> response = employeeController.getEmployeeById(id);

        verify(employeeService, times(1)).getEmployeeById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee1, response.getBody());
    }

    @Test
    @DisplayName("Should save an employee and return the saved employee with status CREATED")
    public void testSaveEmployee() {
        ResponseEntity<Employee> response = employeeController.saveEmployee(employee1);

        verify(employeeService, times(1)).saveOrUpdate(employee1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee1, response.getBody());
    }

    @Test
    @DisplayName("Should update an existing employee and return the updated employee with status OK")
    public void testUpdateEmployee() {
        // Configurar el mock para devolver true cuando se verifica la existencia del empleado
        when(employeeService.existsEmployeeById(employee1.getId())).thenReturn(true);

        // Configurar el mock para devolver el empleado actualizado
        when(employeeService.saveOrUpdate(employee1)).thenReturn(employee1);

        // Llamar al método bajo prueba
        ResponseEntity<Employee> response = employeeController.updateEmployee(employee1);

        // Verificar que el servicio fue llamado correctamente
        verify(employeeService, times(1)).existsEmployeeById(employee1.getId());
        verify(employeeService, times(1)).saveOrUpdate(employee1);

        // Verificar que el código de estado y el cuerpo de la respuesta sean los esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee1, response.getBody());
    }


    @Test
    @DisplayName("Should return status NOT_FOUND when trying to update a non-existing employee")
    public void testUpdateEmployeeNotFound() {
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeService.existsEmployeeById(employee.getId())).thenReturn(false);

        ResponseEntity<Employee> response = employeeController.updateEmployee(employee);

        verify(employeeService, times(1)).existsEmployeeById(employee.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should delete an employee and return status NO_CONTENT")
    public void testDeleteEmployee() {
        Long id = employee1.getId();

        ResponseEntity<Void> response = employeeController.delete(id);

        verify(employeeService, times(1)).deleteEmployee(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return status NOT_FOUND when trying to delete a non-existing employee")
    public void testDeleteEmployeeNotFound() {
        Long id = 1L;
        doThrow(IllegalArgumentException.class).when(employeeService).deleteEmployee(id);

        ResponseEntity<Void> response = employeeController.delete(id);

        verify(employeeService, times(1)).deleteEmployee(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should handle DataIntegrityViolationException and return the exception message with status CONFLICT")
    public void testDataIntegrityViolationException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Test exception");

        ResponseEntity<String> response = employeeController.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Error de integridad de datos: Test exception", response.getBody());
    }

    @Test
    @DisplayName("Should handle Exception and return the exception message with status INTERNAL_SERVER_ERROR")
    public void testHandleException() {
        Exception ex = new Exception("Test exception");

        ResponseEntity<String> response = employeeController.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error interno del servidor: Test exception", response.getBody());
    }
}

