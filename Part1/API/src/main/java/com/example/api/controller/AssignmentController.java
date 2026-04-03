package com.example.api.controller;

import com.example.api.model.Assignment;
import com.example.api.model.Department;
import com.example.api.model.Employee;
import com.example.api.repository.AssignmentRepository;
import com.example.api.repository.DepartmentRepository;
import com.example.api.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public AssignmentController(AssignmentRepository assignmentRepository,
                                EmployeeRepository employeeRepository,
                                DepartmentRepository departmentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    // 8. POST assign employee to department
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Assignment createAssignment(@RequestBody AssignmentRequest request) {
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
        // 9. DELETE assignment
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteAssignment(@PathVariable Long id) {
            Assignment assignment = assignmentRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found"));
            assignmentRepository.delete(assignment);

        }

        //DTO class for postman JSON body
        public static class AssignmentRequest {
            private Long employeeId;
            private Long departmentId;
            private String role;
            private String accessLevel;

            public Long getEmployeeId() { return employeeId; }
            public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

            public Long getDepartmentId() { return departmentId; }
            public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

            public String getRole() { return role; }
            public void setRole(String role) { this.role = role; }

            public String getAccessLevel() { return accessLevel; }
            public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
        }
    }