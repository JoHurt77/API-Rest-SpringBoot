package com.api.crud.service;

import com.api.crud.entity.Employee;
import com.api.crud.entity.WorkCenter;
import com.api.crud.repository.EmployeeRepository;
import com.api.crud.repository.WorkCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Clase de servicio para realizar operaciones relacionadas con los empleados.
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkCenterRepository workCenterRepository;
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
     * Comprueba si existe un empleado con el ID proporcionado.
     * @param id El ID del empleado a verificar.
     * @return true si el empleado existe, false en caso contrario.
     */
    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    /**
     * Guarda o actualiza un empleado.
     * @param employee Objeto Employee a guardar o actualizar.
     * @return
     */
//    public Employee saveOrUpdate(Employee employee){
//        employeeRepository.save(employee);
//        return employee;
//    }
    public Employee saveOrUpdate(Employee employee){
        // Busca el WorkCenter por id
        WorkCenter workCenter = workCenterRepository.findById(employee.getWorkCenter().getIdWorkCenter())
                .orElseThrow(() -> new IllegalArgumentException("WorkCenter not found by id: " + employee.getWorkCenter().getIdWorkCenter()));

        // Asigna el WorkCenter al empleado
        employee.setWorkCenter(workCenter);

        // Guarda y devuelve el empleado
        employeeRepository.save(employee);
        return employee;
    }

    /**
     * Elimina un empleado por su ID.
     * @param id ID del empleado a eliminar.
     * @return true si se eliminó correctamente, false si no se encontró ningún empleado con el ID proporcionado.
     **/
    public void delete(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Empleado not found by ID : " + id);
        }
    }


}
