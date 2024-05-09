package com.api.crud.exception;

public class WorkCenterNotFoundException extends RuntimeException {
    public WorkCenterNotFoundException(Long id) {
        super("Work Center not found with ID: " + id);
    }
}