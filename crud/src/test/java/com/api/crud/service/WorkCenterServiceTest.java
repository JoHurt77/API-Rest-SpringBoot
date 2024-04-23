package com.api.crud.service;

import com.api.crud.entity.WorkCenter;
import com.api.crud.repository.WorkCenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkCenterServiceTest {

    @Mock
    private WorkCenterRepository workCenterRepository;

    @InjectMocks
    private WorkCenterService workCenterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getAllWorkCenters method")
    void testGetAll() {
        WorkCenter workCenter1 = new WorkCenter();
        WorkCenter workCenter2 = new WorkCenter();
        List<WorkCenter> expected = Arrays.asList(workCenter1, workCenter2);
        when(workCenterRepository.findAll()).thenReturn(expected);

        // When
        List<WorkCenter> result = workCenterService.getAllWorkCenters();

        // Then
        assertEquals(expected, result);
        verify(workCenterRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test getWorkCenterById method when WorkCenter is found")
    void testGetByIdFound() {
        // Given
        WorkCenter workCenter = new WorkCenter();
        when(workCenterRepository.findById(1L)).thenReturn(Optional.of(workCenter));

        // When
        WorkCenter result = workCenterService.getWorkCenterById(1L);

        // Then
        assertEquals(workCenter, result);
        verify(workCenterRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test getWorkCenterById method when WorkCenter is not found")
    void testGetByIdNotFound() {
        // Given
        when(workCenterRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            workCenterService.getWorkCenterById(1L);
        });
        verify(workCenterRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test saveWorkCenter method")
    void testSave() {
        // Given
        WorkCenter workCenter = new WorkCenter();
        when(workCenterRepository.save(workCenter)).thenReturn(workCenter);

        // When
        WorkCenter result = workCenterService.saveWorkCenter(workCenter);

        // Then
        assertEquals(workCenter, result);
        verify(workCenterRepository, times(1)).save(workCenter);
    }

    @Test
    @DisplayName("Test deleteWorkCenter method when WorkCenter is found")
    void testDeleteFound() {
        // Given
        when(workCenterRepository.findById(1L)).thenReturn(Optional.of(new WorkCenter()));

        // When
        workCenterService.deleteWorkCenter(1L);

        // Then
        verify(workCenterRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test deleteWorkCenter method when WorkCenter is not found")
    void testDeleteNotFound() {
        // Given
        when(workCenterRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            workCenterService.deleteWorkCenter(1L);
        });
        verify(workCenterRepository, times(1)).findById(1L);
    }
}
