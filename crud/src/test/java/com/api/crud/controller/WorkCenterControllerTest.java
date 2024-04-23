package com.api.crud.controller;

import com.api.crud.entity.WorkCenter;
import com.api.crud.service.WorkCenterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkCenterControllerTest {

    @Mock // Mock del servicio de WorkCenter
    private WorkCenterService workCenterService;

    @InjectMocks // Inyección de los mocks en el controlador de WorkCenter
    private WorkCenterController workCenterController;

    /**
     * Se configura el comportamiento del servicio con 'when',
     * se llama al método del controlador, y luego se verifican
     * los resultados y el comportamiento con 'assertEquals' y 'verify'.
     */
    @Test
    @DisplayName("Should return all work centers")
    public void testGetAllWorkCenters() {
        List<WorkCenter> workCenters = new ArrayList<>();
        when(workCenterService.getAllWorkCenters()).thenReturn(workCenters);

        ResponseEntity<List<WorkCenter>> response = workCenterController.getAllWorkCenters();

        verify(workCenterService, times(1)).getAllWorkCenters();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workCenters, response.getBody());
    }

    @Test
    @DisplayName("Should return work center by id")
    public void testGetWorkCenterById() {
        WorkCenter workCenter = new WorkCenter();
        // set properties for workCenter
        Long id = 1L;
        when(workCenterService.getWorkCenterById(id)).thenReturn(workCenter);

        ResponseEntity<WorkCenter> response = workCenterController.getWorkCenterById(id);

        verify(workCenterService, times(1)).getWorkCenterById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workCenter, response.getBody());
    }
    @Test
    @DisplayName("Should save work center")
    public void testSaveWorkCenter() {
        WorkCenter workCenter = new WorkCenter();
        // set properties for workCenter
        when(workCenterService.saveWorkCenter(any(WorkCenter.class))).thenReturn(workCenter);

        ResponseEntity<WorkCenter> response = workCenterController.saveWorkCenter(workCenter);

        verify(workCenterService, times(1)).saveWorkCenter(any(WorkCenter.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(workCenter, response.getBody());
    }

    @Test
    @DisplayName("Should update work center")
    public void testUpdateWorkCenter() {
        Long id = 1L;
        WorkCenter existingWorkCenter = new WorkCenter();
        WorkCenter updatedWorkCenter = new WorkCenter();
        // set properties for existingWorkCenter and updatedWorkCenter
        when(workCenterService.getWorkCenterById(id)).thenReturn(existingWorkCenter);
        when(workCenterService.saveWorkCenter(any(WorkCenter.class))).thenReturn(updatedWorkCenter);

        ResponseEntity<WorkCenter> response = workCenterController.updateWorkCenter(id, updatedWorkCenter);

        verify(workCenterService, times(1)).getWorkCenterById(id);
        verify(workCenterService, times(1)).saveWorkCenter(any(WorkCenter.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedWorkCenter, response.getBody());
    }

    @Test
    @DisplayName("Should delete work center")
    public void testDeleteWorkCenter() {
        Long id = 1L;
        doNothing().when(workCenterService).deleteWorkCenter(id);

        ResponseEntity<Void> response = workCenterController.deleteWorkCenter(id);

        verify(workCenterService, times(1)).deleteWorkCenter(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Should handle runtime exception")
    public void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Test exception");

        ResponseEntity<String> response = workCenterController.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Test exception", response.getBody());
    }

}
