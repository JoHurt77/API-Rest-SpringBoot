package com.api.crud.model;

import com.api.crud.entity.Department;
import com.api.crud.entity.WorkCenter;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private Long workCenterId;
    private Long departmentId;
}
