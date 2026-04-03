package com.example.api.controller;

import com.example.api.model.Department;
import com.example.api.model.Employee;
import com.example.api.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // 6. GET all departments
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // 7. POST create department
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) {
        if (department.getBudget() <= 0) {
            throw new IllegalArgumentException("Budget must be positive");
        }
        return departmentRepository.save(department);
    }


    //  list employees in a department
    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesInDepartment(@PathVariable Long id) {
        return departmentRepository.findEmployeesByDepartmentId(id);
    }
}