package com.api.crud.controller;

import com.api.crud.entity.WorkCenter;
import com.api.crud.service.WorkCenterService;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkCenterControllerTest {

    @Mock // Mock del servicio de WorkCenter
    private WorkCenterService workCenterService;

    @InjectMocks // Inyección de los mocks en el controlador de WorkCenter
    private WorkCenterController workCenterController;

    private WorkCenter workCenter1;

    @BeforeEach
    public void setUp() {
        // Inicializar un WorkCenter para usar en todas las pruebas
        workCenter1 = new WorkCenter();
        workCenter1.setId(1L);
        workCenter1.setLocation("Spain");

        // Configurar el mock para devolver workCenter1 cuando se llama a getWorkCenterById(1L)
        lenient().when(workCenterService.getWorkCenterById(1L)).thenReturn(workCenter1);

        // Configurar el mock para guardar workCenter1 y devolverlo
        lenient().when(workCenterService.saveWorkCenter(workCenter1)).thenReturn(workCenter1);

        // Configurar el mock para realizar una operación de eliminación sin hacer nada
        lenient().doNothing().when(workCenterService).deleteWorkCenter(1L);
    }

    @Test
    @DisplayName("Should return all work centers")
    public void testGetAllWorkCenters() {
        // Crear una lista de WorkCenters que incluye workCenter1
        List<WorkCenter> workCenters = Collections.singletonList(workCenter1);
        when(workCenterService.getAllWorkCenters()).thenReturn(workCenters);

        ResponseEntity<List<WorkCenter>> response = workCenterController.getAllWorkCenters();

        verify(workCenterService, times(1)).getAllWorkCenters();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workCenters, response.getBody());
    }

    @Test
    @DisplayName("Should return work center by id")
    public void testGetWorkCenterById() {
        Long id = 1L;

        ResponseEntity<WorkCenter> response = workCenterController.getWorkCenterById(id);

        verify(workCenterService, times(1)).getWorkCenterById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workCenter1, response.getBody());
    }

    @Test
    @DisplayName("Should save work center")
    public void testSaveWorkCenter() {
        ResponseEntity<WorkCenter> response = workCenterController.saveWorkCenter(workCenter1);

        verify(workCenterService, times(1)).saveWorkCenter(workCenter1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(workCenter1, response.getBody());
    }

    @Test
    @DisplayName("Should update work center")
    public void testUpdateWorkCenter() {
        Long id = 1L;
        WorkCenter updatedWorkCenter = new WorkCenter();
        updatedWorkCenter.setId(id);
        updatedWorkCenter.setLocation("New Location");
        // Establecer otras propiedades necesarias para updatedWorkCenter

        // Configurar el mock para devolver el Work Center existente y actualizado
        when(workCenterService.saveWorkCenter(updatedWorkCenter)).thenReturn(updatedWorkCenter);

        ResponseEntity<WorkCenter> response = workCenterController.updateWorkCenter(id, updatedWorkCenter);

        verify(workCenterService, times(1)).getWorkCenterById(id);
        verify(workCenterService, times(1)).saveWorkCenter(updatedWorkCenter);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedWorkCenter, response.getBody());
    }

    @Test
    @DisplayName("Should delete work center")
    public void testDeleteWorkCenter() {
        Long id = workCenter1.getId();

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

