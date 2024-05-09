package com.api.crud.service;

import com.api.crud.entity.Employee;
import com.api.crud.exception.DepartmentNotFoundException;
import com.api.crud.exception.EmployeeNotFoundException;
import com.api.crud.exception.WorkCenterNotFoundException;
import com.api.crud.repository.DepartmentRepository;
import com.api.crud.repository.EmployeeRepository;
import com.api.crud.repository.WorkCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkCenterRepository workCenterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    /**
     * Obtiene todos los empleados.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Obtiene un empleado por su ID.
     * @param id ID del empleado a obtener.
     * @return Optional que contiene el empleado si se encuentra, o vacío si no se encuentra.
     */
    public Optional<Employee> getEmployeeById(Long id) {
        return Optional.ofNullable(employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
    }

    /**
     * Guarda o actualiza un empleado.
     * @param employee Objeto Employee a guardar o actualizar.
     * @return Employee guardado o actualizado.
     */
    public Employee saveOrUpdate(Employee employee) {
        // Verificar si el WorkCenter del empleado existe
        Long workCenterId = employee.getWorkCenter().getId();
        if (!workCenterRepository.existsById(workCenterId)) {
            throw new WorkCenterNotFoundException(workCenterId);
        }

        // Verificar si el Departamento del empleado existe
        Long departmentId = employee.getDepartment().getId();
        if (!departmentRepository.existsById(departmentId)) {
            throw new DepartmentNotFoundException(departmentId);
        }

        return employeeRepository.save(employee);
    }


    /**
     * Elimina un empleado por su ID.
     * @param id ID del empleado a eliminar.
     */
    public void deleteEmployee(Long id) {
        if (existsEmployeeById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException(id);
        }
    }

    /**
     * Comprueba si existe un empleado con el ID proporcionado.
     * @param id El ID del empleado a verificar.
     * @return true si el empleado existe, false en caso contrario.
     */
    public boolean existsEmployeeById(Long id) {
        return employeeRepository.existsById(id);
    }
}
