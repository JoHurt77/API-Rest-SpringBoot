# API REST para Empleados

Esta API REST fue desarrollada utilizando Spring Boot y se utiliza para gestionar operaciones CRUD relacionadas con los empleados.

## Endpoints

La API consta de los siguientes endpoints:

- `GET /employee`: Devuelve un mensaje de bienvenida.
- `GET /employee/list`: Devuelve una lista de todos los empleados. Si no hay empleados, devuelve un estado HTTP 204 (NO_CONTENT).
- `GET /employee/list/{employeeId}`: Devuelve un empleado específico por su ID. Si el empleado no se encuentra, devuelve un estado HTTP 404 (NOT_FOUND).
- `POST /employee/save`: Guarda un nuevo empleado. En caso de error, devuelve un estado HTTP 500 (INTERNAL_SERVER_ERROR).
- `PUT /employee/update`: Actualiza un empleado existente. Es necesario introducir todos los datos del empleado incluyendo el ID. Si el empleado no existe, devuelve un estado HTTP 404 (NOT_FOUND).
- `DELETE /employee/delete/{employeeId}`: Elimina un empleado específico por su ID. Si el empleado no se encuentra, devuelve un estado HTTP 404 (NOT_FOUND).

## BBDD

La API utiliza una base de datos nombrada como "coforge". Conexión con Mariadb.

![image](https://github.com/JoHurt77/crud/assets/117202441/d964debb-a0b2-43c9-9193-efc41144a2d0)

