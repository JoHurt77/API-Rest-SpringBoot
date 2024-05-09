package com.api.crud.controller;

import com.api.crud.entity.Department;
import com.api.crud.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private Department department1;

    @BeforeEach
    public void setUp() {
        // Inicializar un departamento para usar en todas las pruebas
        department1 = new Department();
        department1.setId(1L);
        department1.setName("Developers");

        // Configurar el mock para devolver el departamento usando
        lenient().when(departmentService.getDepartmentById(1L)).thenReturn(department1);
    }


    @DisplayName("Should get a list of departments")
    @Test
    public void testGetAllDepartments() {
        // Crear otro departamento para probar la lista
        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("Marketing");

        // Crear lista de departamentos
        List<Department> departmentList = Arrays.asList(department1, department2);

        // Configurar el mock para devolver la lista de departamentos
        when(departmentService.getAllDepartments()).thenReturn(departmentList);

        // Llamar al método bajo prueba
        ResponseEntity<List<Department>> response = departmentController.getAllDepartments();

        // Verificar que se llamó al método del mock correctamente
        verify(departmentService, times(1)).getAllDepartments();

        // Verificar que el código de estado y el cuerpo de la respuesta son los esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentList, response.getBody());
    }

    @DisplayName("Should show a department according to the id")
    @Test
    public void testGetDepartmentById() {
        // Llamar al método bajo prueba con el id predefinido
        ResponseEntity<Department> response = departmentController.getDepartmentById(department1.getId());

        // Verificar que el método del mock fue llamado correctamente
        verify(departmentService, times(1)).getDepartmentById(department1.getId());

        // Verificar que el código de estado y el cuerpo de la respuesta son los esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department1, response.getBody());
    }

    @DisplayName("Should throw an exception if there isn't a department according to the id")
    @Test
    public void testGetDepartmentByIdNotFound() {
        // Preparar datos de prueba
        Long id = 1L;

        // Configurar el mock para devolver null cuando se busca un departamento inexistente
        when(departmentService.getDepartmentById(id)).thenReturn(null);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.getDepartmentById(id);

        // Verificar que el método del mock fue llamado correctamente
        verify(departmentService, times(1)).getDepartmentById(id);

        // Verificar que el código de estado de la respuesta es NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @DisplayName("Should save a department and return the saved department")
    @Test
    public void testSaveDepartment() {
        // Configurar el mock para devolver el departamento guardado
        when(departmentService.saveDepartment(department1)).thenReturn(department1);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.saveDepartment(department1);

        // Verificar que el método del mock fue llamado correctamente
        verify(departmentService, times(1)).saveDepartment(department1);

        // Verificar que el código de estado y el cuerpo de la respuesta son los esperados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(department1, response.getBody());
    }

    @DisplayName("Should update a department according to the id")
    @Test
    public void testUpdateDepartment() {
        // Preparar datos de prueba
        Long id = 1L;
        Department departmentDetails = new Department();
        departmentDetails.setName("Updated Name");

        // Configurar el mock para devolver el departamento existente
        when(departmentService.getDepartmentById(id)).thenReturn(department1);
        when(departmentService.saveDepartment(department1)).thenReturn(department1);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.updateDepartment(id, departmentDetails);

        // Verificar que los métodos del mock fueron llamados correctamente
        verify(departmentService, times(1)).getDepartmentById(id);
        verify(departmentService, times(1)).saveDepartment(department1);

        // Verificar que el código de estado y el cuerpo de la respuesta son los esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department1, response.getBody());
    }

    @DisplayName("Should return status NOT_FOUND when trying to update a non-existing department")
    @Test
    public void testUpdateDepartmentNotFound() {
        // Preparar datos de prueba
        Long id = 1L;
        Department departmentDetails = new Department();
        departmentDetails.setName("Updated Name");

        // Configurar el mock para devolver null cuando se busca un departamento inexistente
        when(departmentService.getDepartmentById(id)).thenReturn(null);

        // Llamar al método bajo prueba
        ResponseEntity<Department> response = departmentController.updateDepartment(id, departmentDetails);

        // Verificar que el método del mock fue llamado correctamente
        verify(departmentService, times(1)).getDepartmentById(id);

        // Verificar que el código de estado de la respuesta es NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @DisplayName("Should delete a department and return status NO_CONTENT")
    @Test
    public void testDeleteDepartment() {
        // Preparar datos de prueba
        Long id = department1.getId();

        // Llamar al método bajo prueba
        ResponseEntity<Void> response = departmentController.deleteDepartment(id);

        // Verificar que el método del mock fue llamado correctamente
        verify(departmentService, times(1)).deleteDepartment(id);

        // Verificar que el código de estado de la respuesta es NO_CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("Should handle RuntimeException and return the exception message with status INTERNAL_SERVER_ERROR")
    @Test
    public void testHandleRuntimeException() {
        // Preparar datos de prueba
        RuntimeException ex = new RuntimeException("Test exception");

        // Llamar al método bajo prueba
        ResponseEntity<String> response = departmentController.handleRuntimeException(ex);

        // Verificar que el código de estado de la respuesta es INTERNAL_SERVER_ERROR
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verificar que el cuerpo de la respuesta contiene el mensaje de la excepción
        assertEquals("Test exception", response.getBody());
    }
}
