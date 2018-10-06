package com.eizzy.employee.repo;

import com.eizzy.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Integer>{

    Optional<Employee> findByFirstNameIgnoreCase(String firstName);
}
