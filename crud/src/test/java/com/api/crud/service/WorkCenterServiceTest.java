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
    @DisplayName("Should get all WorkCenters")
    void testGetAll() {
        WorkCenter workCenter1 = new WorkCenter();
        WorkCenter workCenter2 = new WorkCenter();
        List<WorkCenter> expected = Arrays.asList(workCenter1, workCenter2);
        when(workCenterRepository.findAll()).thenReturn(expected);

        List<WorkCenter> result = workCenterService.getAllWorkCenters();

        assertEquals(expected, result);
        verify(workCenterRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get a WorkCenter by Id")
    void testGetByIdFound() {
        WorkCenter workCenter = new WorkCenter();
        when(workCenterRepository.findById(1L)).thenReturn(Optional.of(workCenter));

        WorkCenter result = workCenterService.getWorkCenterById(1L);

        assertEquals(workCenter, result);
        verify(workCenterRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throws an exception when a WorkCenter ByID is Not found")
    void testGetByIdNotFound() {
        when(workCenterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            workCenterService.getWorkCenterById(1L);
        });
        verify(workCenterRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should save a WorkCenter")
    void testSave() {
        WorkCenter workCenter = new WorkCenter();
        when(workCenterRepository.save(workCenter)).thenReturn(workCenter);

        WorkCenter result = workCenterService.saveWorkCenter(workCenter);

        assertEquals(workCenter, result);
        verify(workCenterRepository, times(1)).save(workCenter);
    }

    @Test
    @DisplayName("Should delete a WorkCenter when is found")
    void testDeleteFound() {
        when(workCenterRepository.findById(1L)).thenReturn(Optional.of(new WorkCenter()));

        workCenterService.deleteWorkCenter(1L);

        verify(workCenterRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw an exception when trying to DELETE a WorkCenter is Not found")
    void testDeleteNotFound() {
        when(workCenterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            workCenterService.deleteWorkCenter(1L);
        });
        verify(workCenterRepository, times(1)).findById(1L);
    }
}
