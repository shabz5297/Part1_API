package com.example.api.model;

import jakarta.persistence.*;

@Entity
@Table
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //one employee can have many assigments
    @JoinColumn(name="employee_id", nullable = false) // Tells hibernate to create a column
    private Employee employee;

    @ManyToOne //one department can have many assignment
    @JoinColumn(name="department_id", nullable = false)
    private Department department;

    private String role;
    private String accessLevel;

    public Assignment(Employee employee, Department department, String role, String accessLevel) {
        this.employee = employee;
        this.department = department;
        this.role = role;
        this.accessLevel = accessLevel;
    }

    //No argument constructor
    public Assignment() {
    }


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Employee getEmployee() {return employee;}
    public void setEmployee(Employee employee) {this.employee = employee;}

    public Department getDepartment() {return department;}
    public void setDepartment(Department department) {this.department = department;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public String getAccessLevel() {return accessLevel;}
    public void setAccessLevel(String accessLevel) {this.accessLevel = accessLevel;}
}
