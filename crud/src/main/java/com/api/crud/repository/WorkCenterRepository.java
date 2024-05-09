package com.api.crud.repository;

import com.api.crud.entity.WorkCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkCenterRepository extends JpaRepository<WorkCenter, Long> {
}
