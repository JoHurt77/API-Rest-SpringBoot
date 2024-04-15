package com.api.crud.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

@Data //para los getters y setters explicitos
@Entity
public class Employee {

    @Id //Id único que se genera automáticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String email;

    //Para añadir el centro del empleado
    @ManyToOne
    //Especifica el campo por el cual se une la entidad
    @JoinColumn(name = "idWorkCenter")
    //se utiliza para manejar las referencias circulares. Evita que Jackson serialice el objeto workCenter
    @JsonBackReference
    private WorkCenter workCenter;

    // Getter personalizado para el idWorkCenter
    @JsonProperty("idWorkCenter")
    public Long getWorkCenterId() {
        return workCenter != null ? workCenter.getIdWorkCenter() : null;
    }
}
