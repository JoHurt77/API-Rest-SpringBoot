package com.api.crud.service;

import com.api.crud.entity.Department;
import com.api.crud.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDepartments_ShouldReturnListOfDepartments() {
        Department department1 = new Department();
        Department department2 = new Department();
        List<Department> expected = Arrays.asList(department1, department2);
        when(departmentRepository.findAll()).thenReturn(expected);

        // When
        List<Department> result = departmentService.getAllDepartments();

        // Then
        assertEquals(expected, result);
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getDepartmentById_ShouldReturnDepartment_WhenFound() {
        // Given
        Department department = new Department();
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        // When
        Department result = departmentService.getDepartmentById(1L);

        // Then
        assertEquals(department, result);
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void getDepartmentById_ShouldThrowException_WhenNotFound() {
        // Given
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            departmentService.getDepartmentById(1L);
        });
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void saveDepartment_ShouldSaveDepartment() {
        // Given
        Department department = new Department();
        when(departmentRepository.save(department)).thenReturn(department);

        // When
        Department result = departmentService.saveDepartment(department);

        // Then
        assertEquals(department, result);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void deleteDepartment_ShouldDeleteDepartment_WhenFound() {
        // Given
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(new Department()));

        // When
        departmentService.deleteDepartment(1L);

        // Then
        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteDepartment_ShouldThrowException_WhenNotFound() {
        // Given
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            departmentService.deleteDepartment(1L);
        });
        verify(departmentRepository, times(1)).findById(1L);
    }
}
