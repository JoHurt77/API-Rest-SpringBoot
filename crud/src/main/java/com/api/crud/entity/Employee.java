package com.api.crud.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data //para los getters y setters automaticos
@Entity
public class Employee {

    @Id //Id único que se genera automáticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String email;
}
