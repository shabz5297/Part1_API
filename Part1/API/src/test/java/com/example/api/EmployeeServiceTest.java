package com.example.api.service;

import com.example.api.model.Employee;
import com.example.api.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void promoteEmployee_shouldThrow_ifEmployeeDoesNotExist() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                employeeService.promoteEmployee(1L));

        assertEquals("404 NOT_FOUND \"Employee not found\"", exception.getMessage());
    }

    @Test
    void promoteEmployee_shouldThrow_ifEmployeeNotEligible() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setStartDate(LocalDate.now().minusMonths(2));
        employee.setSalary(30000);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                employeeService.promoteEmployee(1L));

        assertEquals("400 BAD_REQUEST \"Employee not eligible for promotion\"", exception.getMessage());
    }

    @Test
    void promoteEmployee_shouldIncreaseSalary_ifEligible() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setStartDate(LocalDate.now().minusMonths(8));
        employee.setSalary(30000);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = employeeService.promoteEmployee(1L);

        assertEquals(33000, updatedEmployee.getSalary());
    }
}