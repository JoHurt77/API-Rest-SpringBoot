package com.api.crud.controller;

import com.api.crud.entity.Employee;
import com.api.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controlador REST para gestionar operaciones CRUD relacionadas con los empleados.
@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //mensaje de bienvenida.
    @GetMapping
    public String hello() {
        return "Bienvenido a la API Rest para empleados!";
    }

    //Lista todos los empleados.
    @GetMapping("/list")
    public List<Employee> getAll(){
        return employeeService.getEmployee();
    }

    /**
     * Obtiene un empleado por su ID.
     * @param id = ID del empleado a obtener.
     * @return ResponseEntity que contiene el empleado si se encuentra, o un código de estado HTTP 404 si no se encuentra.
     */
    @GetMapping("/list/{employeeId}")
    public ResponseEntity<Employee> getById(@PathVariable("employeeId") Long id){
        Employee employee = employeeService.getEmployee(id).orElse(null);
        if(employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Guardar un empleado.
     */
    @PostMapping("/save")
    public void save(@RequestBody Employee employee){
        employeeService.saveOrUpdate(employee);
    }

    /**
     * Actualizar un empleado.
     * Es necesario introducir todos los datos del empleado incluyendo el id.
     */
    @PutMapping("/update")
    public void update(@RequestBody Employee employee) {
        employeeService.saveOrUpdate(employee);
    }

    /**
     * Elimina un empleado por su ID.
     * @param id ID del empleado a eliminar.
     */
//    @DeleteMapping("/delete/{employeeId}")
//    public void delete(@PathVariable("employeeId") Long id){
//        employeeService.delete(id);
//    }
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> delete(@PathVariable("employeeId") Long id){
        try {
            employeeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
