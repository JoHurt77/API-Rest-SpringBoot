package com.api.crud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data //para los getters y setters explicitos
@Entity
public class Employee {

    @Id //Id único que se genera automáticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String firstName;
    @Column(nullable = false)
    @NotBlank
    private String lastName;
    @Column(nullable = false)
    @NotBlank
    private String address;
    @Column(nullable = false)
    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "workCenter_id", nullable = false)
    private WorkCenter workCenter;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    //Para añadir el centro del empleado
    //Especifica el campo por el cual se une la entidad
//    @JoinColumn(name = "idWorkCenter")
//    //se utiliza para manejar las referencias circulares. Evita que Jackson serialice el objeto workCenter
//    @JsonBackReference


    // Getter personalizado para el idWorkCenter
//    @JsonProperty("idWorkCenter")
//    public Long getWorkCenterId() {
//        return workCenter != null ? workCenter.getIdWorkCenter() : null;
//    }
}
