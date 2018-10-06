package com.eizzy.employee.repo;

import com.eizzy.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer>{

    Optional<Employee> findByFirstNameIgnoreCase(String firstName);
}
