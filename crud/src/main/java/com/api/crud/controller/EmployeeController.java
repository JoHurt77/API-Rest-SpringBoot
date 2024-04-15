package com.api.crud.controller;

import com.api.crud.entity.Employee;
import com.api.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Controlador REST para gestionar operaciones CRUD relacionadas con los empleados.
@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //mensaje de bienvenida.
    @GetMapping
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Bienvenido a la API Rest para empleados!", HttpStatus.OK);
    }

    /**
     * Lista todos los empleados.
     * @return
    **/
    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getAll(){
        List<Employee> employees = employeeService.getEmployee();
        if(employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Obtiene un empleado por su ID.
     * @param id = ID del empleado a obtener.
     * @return ResponseEntity que contiene el empleado si se encuentra, o un código de estado HTTP 404 si no se encuentra.
     */
    @GetMapping("/list/{employeeId}")
    public ResponseEntity<Employee> getById(@PathVariable("employeeId") Long id){
        Optional<Employee> employee = employeeService.getEmployee(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Guardar un empleado.
     */
    @PostMapping("/save")
    public ResponseEntity<Employee> save(@RequestBody Employee employee){
        try {
            Employee savedEmployee = employeeService.saveOrUpdate(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualizar un empleado.
     * Es necesario introducir todos los datos del empleado incluyendo el id.
     */
    @PutMapping("/update")
    public ResponseEntity<Employee> update(@RequestBody Employee employee) {
        if(employeeService.existsById(employee.getId())) {
            try {
                Employee updatedEmployee = employeeService.saveOrUpdate(employee);
                return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un empleado por su ID.
     * @param id ID del empleado a eliminar.
     */
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
