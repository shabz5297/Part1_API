package com.example.api.service;

import com.example.api.model.Employee;
import com.example.api.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(String department) {
        if (department != null && !department.isBlank()) {
            return employeeRepository.findByDepartmentName(department);
        }
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updated) {
        Employee employee = getEmployeeById(id);
        employee.setName(updated.getName());
        employee.setContractType(updated.getContractType());
        employee.setEmail(updated.getEmail());
        employee.setSalary(updated.getSalary());
        employee.setStartDate(updated.getStartDate());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    public Employee promoteEmployee(Long id) {
        Employee employee = getEmployeeById(id);

        if (employee.getStartDate() == null || employee.getStartDate().isAfter(LocalDate.now().minusMonths(6))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee not eligible for promotion");
        }

        if (employee.getSalary() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee salary is missing");
        }

        employee.setSalary((int) Math.round(employee.getSalary() * 1.10));

        String currentRole = employee.getRole() == null ? "Employee" : employee.getRole();
        if (!currentRole.startsWith("Senior ")) {
            employee.setRole("Senior " + currentRole);
        }

        return employeeRepository.save(employee);
    }
}
