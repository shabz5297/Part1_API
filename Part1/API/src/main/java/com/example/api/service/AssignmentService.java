package com.example.api.service;

import com.example.api.controller.AssignmentController;
import com.example.api.model.Assignment;
import com.example.api.model.Department;
import com.example.api.model.Employee;
import com.example.api.repository.AssignmentRepository;
import com.example.api.repository.DepartmentRepository;
import com.example.api.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository,
                             EmployeeRepository employeeRepository,
                             DepartmentRepository departmentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Assignment createAssignment(AssignmentController.AssignmentRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));

        Assignment assignment = new Assignment();
        assignment.setEmployee(employee);
        assignment.setDepartment(department);
        assignment.setRole(request.getRole());
        assignment.setAccessLevel(request.getAccessLevel());
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found"));
        assignmentRepository.delete(assignment);
    }
}
