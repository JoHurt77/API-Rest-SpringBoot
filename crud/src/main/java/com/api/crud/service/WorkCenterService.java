package com.api.crud.service;

import com.api.crud.entity.WorkCenter;
import com.api.crud.repository.WorkCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkCenterService {
    @Autowired
    private WorkCenterRepository workCenterRepository;

    public List<WorkCenter> getAllWorkCenters() {
        return workCenterRepository.findAll();
    }

    public WorkCenter getWorkCenterById(Long id) {
        Optional<WorkCenter> workCenter = workCenterRepository.findById(id);
        if (workCenter.isPresent()) {
            return workCenter.get();
        } else {
            throw new RuntimeException("WorkCenter not found by id : " + id);
        }
    }

    public WorkCenter saveWorkCenter(WorkCenter workCenter) {
        return workCenterRepository.save(workCenter);
    }

    public void deleteWorkCenter(Long id) {
        Optional<WorkCenter> workCenter = workCenterRepository.findById(id);
        if (workCenter.isPresent()) {
            workCenterRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cannot delete. WorkCenter not found by id : " + id);
        }
    }
}
