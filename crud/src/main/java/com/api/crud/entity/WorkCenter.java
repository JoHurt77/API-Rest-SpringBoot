package com.api.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WorkCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWorkCenter;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String address;

    /**
     *  establece una relación de uno a muchos con la entidad Employee
     *  lo mapea por el campo de workCenter
     **/
    @OneToMany(mappedBy = "workCenter")
    @JsonManagedReference
    private List<Employee> employees = new ArrayList<>();
}
