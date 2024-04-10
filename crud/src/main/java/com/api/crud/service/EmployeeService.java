package com.api.crud.service;

import com.api.crud.entity.Employee;
import com.api.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Clase de servicio para realizar operaciones relacionadas con los empleados.
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Obtiene todos los empleados.
     */
    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

    /**
     * Obtiene un empleado por su ID.
     * @param id ID del empleado a obtener.
     * @return Optional que contiene el empleado si se encuentra, o vacío si no se encuentra.
     */
    public Optional<Employee> getEmployee(Long id){
        return employeeRepository.findById(id);
    }

    /**
     * Guarda o actualiza un empleado.
     * @param employee Objeto Employee a guardar o actualizar.
     */
    public void saveOrUpdate(Employee employee){
        employeeRepository.save(employee);
    }

    /**
     * Elimina un empleado por su ID.
     * @param id ID del empleado a eliminar.
     * @throws IllegalArgumentException si no se encuentra ningún empleado con el ID proporcionado.
     */
//    public void delete(Long id){
//        Optional<Employee> employeeOptional = employeeRepository.findById(id);
//        if (employeeOptional.isPresent()) {
//            employeeRepository.deleteById(id);
//        } else {
//            throw new IllegalArgumentException("Empleado con ID " + id + " no encontrado");
//        }
//    }
    /**
     * Elimina un empleado por su ID.
     * @param id ID del empleado a eliminar.
     * @return true si se eliminó correctamente, false si no se encontró ningún empleado con el ID proporcionado.
    public void delete(Long id) {
    Optional<Employee> employeeOptional = employeeRepository.findById(id);
    if (employeeOptional.isPresent()) {
    employeeRepository.deleteById(id);
    } else {
    throw new IllegalArgumentException("Empleado con ID " + id + " no encontrado");
    }
    */

    public void delete(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Empleado con ID " + id + " no encontrado");
        }
    }

}
