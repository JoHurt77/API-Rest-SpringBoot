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
    void ShouldReturnListOfDepartments() {
        Department department1 = new Department();
        Department department2 = new Department();
        List<Department> expected = Arrays.asList(department1, department2);
        when(departmentRepository.findAll()).thenReturn(expected);

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(expected, result);
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void ShouldReturnDepartment_WhenFound() {
        Department department = new Department();
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(1L);

        assertEquals(department, result);
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void ShouldThrowException_WhenNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            departmentService.getDepartmentById(1L);
        });
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void ShouldSaveDepartment() {
        Department department = new Department();
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = departmentService.saveDepartment(department);

        assertEquals(department, result);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void ShouldDeleteDepartment_WhenFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(new Department()));

        departmentService.deleteDepartment(1L);

        verify(departmentRepository, times(1)).deleteById(1L);
    }

}
