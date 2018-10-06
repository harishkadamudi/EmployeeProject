package com.eizzy.employee.controller;

import com.eizzy.employee.model.Employee;
import com.eizzy.employee.repo.EmployeeRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    private EmployeeRepo employeeRepo;

    EmployeeController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    /**
     * API used to create a Employee
     *
     * @param employee
     * @return ResponseEntity of Employee
     */
    @PostMapping("/employees")
    public ResponseEntity<Optional<Employee>> createEmployee(@RequestBody Employee employee) {
        Employee save = employeeRepo.save(employee);
        return ResponseEntity.ok(java.util.Optional.ofNullable(save));
    }

    @PutMapping("/employees")
    public ResponseEntity<Optional<Employee>> updateEmployee(@RequestBody Employee employee) {

        Optional<Employee> byId = employeeRepo.findById(employee.getEmployeeId());
        if (byId.isPresent()) {
            Employee emp = byId.get();
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            final Employee save = employeeRepo.saveAndFlush(emp);
            return ResponseEntity.ok(java.util.Optional.ofNullable(save));
        }else {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * API used to create bulk Employees
     *
     * @param employees
     * @return return number of employees created.
     */
    @PostMapping("/employees/bulk")
    public ResponseEntity<String> createBulkEmployee(@RequestBody List<Employee> employees) {
        Optional<List<Employee>> list = Optional.of(employeeRepo.saveAll(employees));
        long count = list.orElse(Collections.emptyList()).stream().count();
        return ResponseEntity.ok("No. Of Employees Inserted :" + count);
    }


    /**
     * API used to get/display all Employees
     *
     * @return list of employees
     */
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> employees() {
        List<Employee> employees = employeeRepo.findAll();
        return ResponseEntity.ok(employees);
    }

    /**
     * API used to find employee based on employeeId
     *
     * @param employeeId
     * @return if found return employee or else empty employee object
     */
    @GetMapping("/employees/{employeeId:[0-9]+}")
    public ResponseEntity<Employee> employee(@PathVariable Integer employeeId) {
        return buildResponse(employeeRepo.findById(employeeId));

    }

    /**
     * API used to find employee based on employee firstname
     *
     * @param firstname
     * @return if found return employee or else empty employee object
     */

    @GetMapping("/employees/{firstname:[a-zA-Z]+}")
    public ResponseEntity<Employee> employeeByFirstName(@PathVariable String firstname) {
        return buildResponse(employeeRepo.findByFirstNameIgnoreCase(firstname));
    }

    /**
     * This API is used to delete employee based on the employeeId
     *
     * @param employeeId
     * @return String stating deletion operation along with different status codes
     */
    @DeleteMapping("/employees/{employeeId:[0-9]+}")
    public ResponseEntity<String> delete(@PathVariable Integer employeeId) {
        try {
            employeeRepo.deleteById(employeeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn\'t Delete");
        }
        return ResponseEntity.ok("Deleted!");
    }


    private ResponseEntity<Employee> buildResponse(Optional<Employee> employee) {
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else
            return ResponseEntity.noContent().build();
    }
}
