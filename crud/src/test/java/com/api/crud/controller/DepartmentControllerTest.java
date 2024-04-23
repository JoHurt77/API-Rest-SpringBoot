package com.api.crud.controller;

import com.api.crud.entity.Department;
import com.api.crud.service.DepartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;
    @DisplayName("Should det a list of the departments")
    @Test
    public void testGetAllDepartments() {
        // Preparar datos de prueba
        Department department1 = new Department();
        Department department2 = new Department();
        List<Department> departmentList = Arrays.asList(department1, department2);

        // Configurar el mock para devolver los datos de prueba
        when(departmentService.getAllDepartments()).thenReturn(departmentList);

        // Llamar al método bajo prueba
        ResponseEntity<List<Department>> response = departmentController.getAllDepartments();

        // Verificar que los métodos del mock fueron llamados correctamente
        verify(departmentService, times(1)).getAllDepartments();

        // Asegurar que el resultado es el esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentList, response.getBody());
    }
    @DisplayName("Should show a department according to the id")
    @Test
    public void testGetDepartmentById() {
        // Preparar datos de prueba
        Long id = 1L;
        Department department = new Department();
        department.setId(id);

        // Configurar el mock para devolver los datos de prueba
        when(departmentService.getDepartmentById(id)).thenReturn(department);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.getDepartmentById(id);

        // Verificar que los métodos del mock fueron llamados correctamente
        verify(departmentService, times(1)).getDepartmentById(id);

        // Asegurar que el resultado es el esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody());
    }
    @DisplayName("Should trow an exception if there isn`t a department according to the id")
    @Test
    public void testGetDepartmentByIdNotFound() {
        // Preparar datos de prueba
        Long id = 1L;

        // Configurar el mock para devolver null
        when(departmentService.getDepartmentById(id)).thenReturn(null);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.getDepartmentById(id);

        // Verificar que los métodos del mock fueron llamados correctamente
        verify(departmentService, times(1)).getDepartmentById(id);

        // Asegurar que el resultado es el esperado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @DisplayName("Should save a department and return the saved department")
    @Test
    public void testSaveDepartment() {
        // Preparar datos de prueba
        Department department = new Department();

        // Configurar el mock para devolver los datos de prueba
        when(departmentService.saveDepartment(department)).thenReturn(department);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.saveDepartment(department);

        // Verificar que los métodos del mock fueron llamados correctamente
        verify(departmentService, times(1)).saveDepartment(department);

        // Asegurar que el resultado es el esperado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(department, response.getBody());
    }
    @DisplayName("Should update a department according to the id")
    @Test
    public void testUpdateDepartment() {
        // Preparar datos de prueba
        Long id = 1L;
        Department existingDepartment = new Department();
        Department departmentDetails = new Department();
        departmentDetails.setName("Updated Name");

        // Configurar el mock para devolver los datos de prueba
        when(departmentService.getDepartmentById(id)).thenReturn(existingDepartment);
        when(departmentService.saveDepartment(existingDepartment)).thenReturn(existingDepartment);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.updateDepartment(id, departmentDetails);

        // Verificar que los métodos del mock fueron llamados correctamente
        verify(departmentService, times(1)).getDepartmentById(id);
        verify(departmentService, times(1)).saveDepartment(existingDepartment);

        // Asegurar que el resultado es el esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingDepartment, response.getBody());
    }
    @DisplayName("Should return status NOT_FOUND when trying to update a non-existing department")
    @Test
    public void testUpdateDepartmentNotFound() {
        // Preparar datos de prueba
        Long id = 1L;
        Department departmentDetails = new Department();
        departmentDetails.setName("Updated Name");

        // Configurar el mock para devolver null
        when(departmentService.getDepartmentById(id)).thenReturn(null);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.updateDepartment(id, departmentDetails);

        // Verificar que los métodos del mock fueron llamados correctamente
        verify(departmentService, times(1)).getDepartmentById(id);

        // Asegurar que el resultado es el esperado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @DisplayName("Should delete a department and return status NO_CONTENT")
    @Test
    public void testDeleteDepartment() {
        // Preparar datos de prueba
        Long id = 1L;

        // Llamar al método bajo prueba
        ResponseEntity<Void> response = departmentController.deleteDepartment(id);

        // Verificar que los métodos del mock fueron llamados correctamente
        verify(departmentService, times(1)).deleteDepartment(id);

        // Asegurar que el resultado es el esperado
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @DisplayName("Should handle RuntimeException and return the exception message with status INTERNAL_SERVER_ERROR")
    @Test
    public void testHandleRuntimeException() {
        // Preparar datos de prueba
        RuntimeException ex = new RuntimeException("Test exception");

        // Llamar al método bajo prueba
        ResponseEntity<String> response = departmentController.handleRuntimeException(ex);

        // Asegurar que el resultado es el esperado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Test exception", response.getBody());
    }

}
