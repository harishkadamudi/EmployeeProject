package com.eizzy.employee;

import com.eizzy.employee.model.Employee;
import com.eizzy.employee.repo.EmployeeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = EmployeeProjectApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integration.properties")
public class EmployeeIntegrationTest {

    @LocalServerPort
    private Integer port;

    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Test
    public void whenPostEmployee_200Response() throws Exception{

        Employee employee = new Employee();
        employee.setFirstName("Test FirstName");
        employee.setLastName("Test LastName");
        employee.setEmail("xyz@gmail.com");

        mvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(employee)))
                .andExpect(status().isOk());

    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Test FirstName");
        employee.setLastName("Test LastName");
        employee.setEmail("xyz@gmail.com");
        mvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostEmployee_thenCreateEmployee() throws Exception {
        Employee alex = new Employee();
        alex.setFirstName("Test");
        alex.setLastName("Test");
        alex.setEmail("xyz@gmail.com");
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<Employee>(alex,headers);
        ResponseEntity<String> response = restTemplate.exchange(buildurl("/employees"), HttpMethod.POST, employeeHttpEntity, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void whenPutEmployee_thenUpdateEmployee() throws Exception {
        Employee alex = new Employee();
        alex.setFirstName("Test");
        alex.setLastName("Test");
        alex.setEmail("xyz@gmail.com");
        HttpEntity<Employee> employeeHttpEntity = new HttpEntity<Employee>(alex,headers);
        ResponseEntity<String> response = restTemplate.exchange(buildurl("/employees"), HttpMethod.PUT, employeeHttpEntity, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful());
    }

    private String buildurl(String uri){
        return "http://localhost:"+port+uri;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
