package com.api.crud.service;

import com.api.crud.entity.Employee;
import com.api.crud.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // extensión Mockito para pruebas con mocks
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @DisplayName("Should return a list of employees")
    @Test
    void getEmployeeTest() {
        // Prepara datos de prueba: dos empleados
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> expected = Arrays.asList(employee1, employee2);

        // Configura el mock para devolver los datos de prueba
        when(employeeRepository.findAll()).thenReturn(expected);

        // Ejecuta el método bajo prueba y captura el resultado
        List<Employee> result = employeeService.getAllEmployees();

        // Asegura que el resultado obtenido es igual al esperado
        assertEquals(expected, result);
        // Confirma que se invocó el método findAll() una vez
        verify(employeeRepository, times(1)).findAll();
    }

    @DisplayName("Should return an employee by id")
    @Test
    void testGetEmployeeById() {
        Long id = 1L; // Define un ID de prueba
        Employee employee = new Employee(); // Crea un empleado de prueba
        employee.setId(id); // Asigna el ID al empleado

        // Configura el mock para devolver el empleado cuando se busque por ID
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Ejecuta el método bajo prueba y captura el resultado
        Optional<Employee> result = employeeService.getEmployeeById(id);

        // Confirma que se invocó findById() una vez con el ID dado
        verify(employeeRepository, times(1)).findById(id);
        // Asegura que el resultado está presente y tiene el ID correcto
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @DisplayName("Should be TRUE if there is a match by id")
    @Test
    void testExistsEmployeeById() {
        Long id = 1L; // Define un ID de prueba

        // Configura el mock para devolver true si existe un empleado con el ID dado
        when(employeeRepository.existsById(id)).thenReturn(true);

        // Ejecuta el método bajo prueba y captura el resultado
        boolean exists = employeeService.existsEmployeeById(id);

        // Confirma que se invocó existsById() una vez con el ID dado
        verify(employeeRepository, times(1)).existsById(id);
        // Asegura que el resultado es verdadero
        assertTrue(exists);
    }

    @DisplayName("Should save a new employee or update an existing one by id")
    @Test
    void testSaveOrUpdate() {
        Employee employee = new Employee(); // Crea un empleado de prueba
        employee.setId(1L); // Asigna un ID al empleado

        // Configura el mock para devolver el empleado guardado
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Ejecuta el método bajo prueba y captura el resultado
        Employee result = employeeService.saveOrUpdate(employee);

        // Confirma que se invocó save() una vez con el empleado dado
        verify(employeeRepository, times(1)).save(employee);
        // Asegura que el ID del resultado es igual al del empleado guardado
        assertEquals(employee.getId(), result.getId());
    }

    @DisplayName("Should delete an employee by id")
    @Test
    void testDeleteEmployee() {
        Long id = 1L; // Define un ID de prueba
        Employee employee = new Employee(); // Crea un empleado de prueba
        employee.setId(id); // Asigna el ID al empleado

        // Configura el mock para devolver el empleado cuando se busque por ID
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Ejecuta el método bajo prueba para eliminar el empleado
        employeeService.deleteEmployee(id);

        // Confirma que se invocaron los métodos findById() y deleteById() una vez cada uno
        verify(employeeRepository, times(1)).findById(id);
        verify(employeeRepository, times(1)).deleteById(id);
    }

    @DisplayName("Should throw IllegalArgumentException when trying to delete a non-existing employee")
    @Test
    void testDeleteEmployeeNotFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.deleteEmployee(1L);
        });

        String expectedMessage = "Empleado not found by ID : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
