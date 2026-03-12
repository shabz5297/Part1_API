package com.example.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Email
    private String email;

    @NotNull
    private String contractType;
    private LocalDate startDate;
    private Integer salary;

    @OneToMany // one employee can have many assigned departments
    private List<Assignment> assignments;



    //no argument constructor for Hibernate to create new instance of class
    public Employee() {
    }


    //doesnt include ID as DB will generate automatically
    public Employee(String name, String contractType, String email, LocalDate startDate, Integer salary) {
        this.name = name;
        this.email = email;
        this.contractType = contractType;
        this.startDate = startDate;
        this.salary = salary;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public @NotNull String getContractType() {return contractType;}
    public void setContractType(@NotNull String contractType) {this.contractType = contractType;}

    public LocalDate getStartDate() {return startDate;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

    public Integer getSalary() {return salary;}
    public void setSalary(Integer salary) {this.salary = salary;}

    public List<Assignment> getAssignments() {return assignments;}
    public void setAssignments(List<Assignment> assignments) {this.assignments = assignments;}

}

