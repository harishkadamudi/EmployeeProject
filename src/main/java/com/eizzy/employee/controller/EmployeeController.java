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

    @PostMapping("/employees")
    public ResponseEntity<Optional<Employee>> createEmployee(@RequestBody Employee employee) {
        Employee save = employeeRepo.save(employee);
        return ResponseEntity.ok(java.util.Optional.ofNullable(save));
    }

    @PostMapping("/employees/bulk")
    public ResponseEntity<String> createBulkEmployee(@RequestBody List<Employee> employees) {
        Optional<List<Employee>> list = Optional.of(employeeRepo.saveAll(employees));
        long count = list.orElse(Collections.emptyList()).stream().count();
        return ResponseEntity.ok("No. Of Employees Inserted :" + count);
    }


    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> employees() {
        List<Employee> employees = employeeRepo.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{employeeId:[0-9]+}")
    public ResponseEntity<Employee> employee(@PathVariable Integer employeeId) {
        return buildResponse(employeeRepo.findById(employeeId));

    }

    @GetMapping("/employees/{firstname:[a-zA-Z]+}")
    public ResponseEntity<Employee> employeeByFirstName(@PathVariable String firstname) {
        return buildResponse(employeeRepo.findByFirstNameIgnoreCase(firstname));

    }

    private ResponseEntity<Employee> buildResponse(Optional<Employee> employee) {
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else
            return ResponseEntity.noContent().build();
    }
}
