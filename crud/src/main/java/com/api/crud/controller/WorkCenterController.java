package com.api.crud.controller;

import com.api.crud.entity.WorkCenter;
import com.api.crud.service.WorkCenterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/workCenter")
public class WorkCenterController {

    @Autowired
    private WorkCenterService workCenterService;

    @GetMapping("/list")
    public ResponseEntity<List<WorkCenter>> getAllWorkCenters() {
        List<WorkCenter> workCenters = workCenterService.getAllWorkCenters();
        return new ResponseEntity<>(workCenters, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<WorkCenter> getWorkCenterById(@PathVariable Long id) {
        WorkCenter workCenter = workCenterService.getWorkCenterById(id);
        if (workCenter != null) {
            return new ResponseEntity<>(workCenter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<WorkCenter> saveWorkCenter(@Valid @RequestBody WorkCenter workCenter) {
        WorkCenter savedWorkCenter = workCenterService.saveWorkCenter(workCenter);
        return new ResponseEntity<>(savedWorkCenter, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkCenter> updateWorkCenter(@PathVariable Long id, @Valid @RequestBody WorkCenter workCenterDetails) {
        WorkCenter existingWorkCenter = workCenterService.getWorkCenterById(id);
        if (existingWorkCenter != null) {
            existingWorkCenter.setLocation(workCenterDetails.getLocation());
            WorkCenter updatedWorkCenter = workCenterService.saveWorkCenter(existingWorkCenter);
            return new ResponseEntity<>(updatedWorkCenter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWorkCenter(@PathVariable Long id) {
        workCenterService.deleteWorkCenter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

