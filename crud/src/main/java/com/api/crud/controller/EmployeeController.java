package com.api.crud.controller;

import com.api.crud.entity.Employee;
import com.api.crud.mapper.EmployeeMapper;
import com.api.crud.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Controlador REST para gestionar operaciones CRUD relacionadas con los empleados.
@CrossOrigin
@RestController
@Validated
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

//    @Autowired
//    private EmployeeMapper employeeMapper;

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
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
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
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") Long id){
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Guardar un empleado.
     */
    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.saveOrUpdate(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Manejar violaciones de integridad de datos
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Manejar otros errores internos del servidor
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualizar un empleado.
     * Es necesario introducir todos los datos del empleado incluyendo el id.
     */
    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) {
        if(employeeService.existsEmployeeById(employee.getId())) {
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
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        return new ResponseEntity<>("Error de integridad de datos: " + ex.getMessage(), HttpStatus.CONFLICT);
//    }
//
}
