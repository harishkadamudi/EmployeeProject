package com.eizzy.employee;

import com.eizzy.employee.model.Employee;
import com.eizzy.employee.repo.EmployeeRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Test
    public void findByFirstNameIgnorecase_thenReturnEmployee(){

        //given this employee
        Employee employee = new Employee();
        employee.setFirstName("Test FirstName");
        employee.setLastName("Test LastName");
        employee.setEmail("email@gmail.com");
        entityManager.persist(employee);
        entityManager.flush();

        //when
        Optional<Employee> employeefoundbyfirstname = employeeRepo.findByFirstNameIgnoreCase(employee.getFirstName());

        //then
        assertThat(employeefoundbyfirstname.get().getFirstName()).isEqualTo(employee.getFirstName());

    }
}
