package com.example.api.controller;

import com.example.api.model.Department;
import com.example.api.model.Employee;
import com.example.api.repository.DepartmentRepository;
import com.example.api.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @InjectMocks
    private DepartmentController departmentController;

    private Employee eligibleEmployee;
    private Employee ineligibleEmployee;

    @BeforeEach
    void setUp() {
        // Employee who started more than 6 months ago — eligible for promotion
        eligibleEmployee = new Employee("Alice Jones", "FULL_TIME", "alice@safedispatch.com",
                LocalDate.now().minusMonths(12), 30000, "ROLE_MANAGER");
        eligibleEmployee.setId(1L);

        // Employee who started less than 6 months ago — NOT eligible
        ineligibleEmployee = new Employee("Bob Smith", "FULL_TIME", "bob@safedispatch.com",
                LocalDate.now().minusMonths(2), 25000, "ROLE_MANAGER");
        ineligibleEmployee.setId(2L);
    }

    // --- Promote Endpoint Tests ---

    @Test
    void promoteEmployee_ShouldIncreaseSalaryByTenPercent_WhenEligible() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(eligibleEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));

        Employee result = employeeController.promoteEmployee(1L);

        assertEquals(33000, result.getSalary()); // 30000 * 1.1 = 33000
    }

    @Test
    void promoteEmployee_ShouldUpdateRole_WhenEligible() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(eligibleEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));

        Employee result = employeeController.promoteEmployee(1L);

        assertTrue(result.getRole().startsWith("Senior"));
    }

    @Test
    void promoteEmployee_ShouldThrow400_WhenEmployeeStartedLessThan6MonthsAgo() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(ineligibleEmployee));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> employeeController.promoteEmployee(2L));

        assertEquals(400, exception.getStatusCode().value());
        assertEquals("Employee not eligible for promotion", exception.getReason());
    }

    @Test
    void promoteEmployee_ShouldThrow404_WhenEmployeeDoesNotExist() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> employeeController.promoteEmployee(99L));

        assertEquals(404, exception.getStatusCode().value());
        assertEquals("Employee not found", exception.getReason());
    }

    @Test
    void promoteEmployee_ShouldThrow400_WhenStartDateIsNull() {
        Employee noDateEmployee = new Employee("No Date", "FULL_TIME", "nodate@safedispatch.com",
                null, 20000, "ROLE_MANAGER");
        noDateEmployee.setId(3L);

        when(employeeRepository.findById(3L)).thenReturn(Optional.of(noDateEmployee));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> employeeController.promoteEmployee(3L));

        assertEquals(400, exception.getStatusCode().value());
    }

    // --- Get Employee Tests ---

    @Test
    void getEmployee_ShouldReturnEmployee_WhenFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(eligibleEmployee));

        Employee result = employeeController.getEmployee(1L);

        assertNotNull(result);
        assertEquals("Alice Jones", result.getName());
    }

    @Test
    void getEmployee_ShouldThrow404_WhenNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> employeeController.getEmployee(99L));

        assertEquals(404, exception.getStatusCode().value());
    }

    // --- Get All Employees Tests ---

    @Test
    void getAllEmployees_ShouldReturnAllEmployees_WhenNoDepartmentFilter() {
        when(employeeRepository.findAll()).thenReturn(List.of(eligibleEmployee, ineligibleEmployee));

        List<Employee> result = employeeController.getAllEmployees(null);

        assertEquals(2, result.size());
    }

    @Test
    void getAllEmployees_ShouldFilterByDepartment_WhenDepartmentProvided() {
        when(employeeRepository.findByDepartmentName("Logistics")).thenReturn(List.of(eligibleEmployee));

        List<Employee> result = employeeController.getAllEmployees("Logistics");

        assertEquals(1, result.size());
        assertEquals("Alice Jones", result.get(0).getName());
    }

    // --- Delete Employee Tests ---

    @Test
    void deleteEmployee_ShouldDeleteSuccessfully_WhenFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(eligibleEmployee));

        assertDoesNotThrow(() -> employeeController.deleteEmployee(1L));
        verify(employeeRepository, times(1)).delete(eligibleEmployee);
    }

    @Test
    void deleteEmployee_ShouldThrow404_WhenNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> employeeController.deleteEmployee(99L));

        assertEquals(404, exception.getStatusCode().value());
    }

    // --- Department Tests ---

    @Test
    void createDepartment_ShouldThrowIllegalArgument_WhenBudgetIsZero() {
        Department dept = new Department("Logistics", 0, "Leicester");

        assertThrows(IllegalArgumentException.class,
                () -> departmentController.createDepartment(dept));
    }

    @Test
    void createDepartment_ShouldThrowIllegalArgument_WhenBudgetIsNegative() {
        Department dept = new Department("Logistics", -5000, "Leicester");

        assertThrows(IllegalArgumentException.class,
                () -> departmentController.createDepartment(dept));
    }

    @Test
    void createDepartment_ShouldSaveDepartment_WhenBudgetIsPositive() {
        Department dept = new Department("Logistics", 100000, "Leicester");
        when(departmentRepository.save(any(Department.class))).thenReturn(dept);

        Department result = departmentController.createDepartment(dept);

        assertNotNull(result);
        assertEquals("Logistics", result.getName());
        verify(departmentRepository, times(1)).save(dept);
    }
}
