package com.example.api.service;

import com.example.api.model.Department;
import com.example.api.model.Employee;
import com.example.api.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department createDepartment(Department department) {
        if (department.getBudget() == null || department.getBudget() <= 0) {
            throw new IllegalArgumentException("Budget must be positive");
        }
        return departmentRepository.save(department);
    }

    public List<Employee> getEmployeesInDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }
        return departmentRepository.findEmployeesByDepartmentId(id);
    }
}
