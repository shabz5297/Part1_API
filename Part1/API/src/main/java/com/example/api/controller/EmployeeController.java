package com.example.api.controller;

import com.example.api.model.Employee;
import com.example.api.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController{

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //1. GET all employees (filter by department)
    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(required = false) String department){
        if (department != null) {
            return employeeRepository.findByDepartmentName(department);}
        return employeeRepository.findAll();
    }


    //2. GET employee by ID
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
    return employeeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    // 3. POST create new employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    // 4. PUT update employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updated) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        // Update allowed fields
        employee.setName(updated.getName());
        employee.setContractType(updated.getContractType());
        employee.setEmail(updated.getEmail());
        employee.setSalary(updated.getSalary());
        employee.setStartDate(updated.getStartDate());

        return employeeRepository.save(employee);
    }

    // 5. DELETE employee
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        employeeRepository.delete(employee);
    }

    // 10. PUT promote employee (business logic)
    @PutMapping("/{id}/promote")
    public Employee promoteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        // must have started > 6 months ago
        if (employee.getStartDate() == null || Period.between(employee.getStartDate(), LocalDate.now()).getMonths() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee not eligible for promotion");
        }

        // Apply salary increase (example: +10%)
        employee.setSalary((int) (employee.getSalary() * 1.1));

        // Optionally update role/rank
        employee.setRole("Senior " + employee.getRole());

        return employeeRepository.save(employee);
    }
}
