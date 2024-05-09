package com.api.crud.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id) {
        super("Department not found with ID: " + id);
    }
}
