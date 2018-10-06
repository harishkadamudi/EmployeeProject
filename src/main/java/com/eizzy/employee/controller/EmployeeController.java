package com.eizzy.employee.controller;

import com.eizzy.employee.model.Employee;
import com.eizzy.employee.repo.EmployeeRepo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    private EmployeeRepo employeeRepo;

    EmployeeController(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }
    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Employee>> createEmployee(@RequestBody Employee employee) {
        Employee save = employeeRepo.save(employee);
        return ResponseEntity.ok(java.util.Optional.ofNullable(save));
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Employee>> employees() {
        List<Employee> employees = employeeRepo.findAll();
        return ResponseEntity.ok(employees);
    }


}
