package com.example.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.util.List;

@Entity
@Table
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Positive
    private Integer budget;

    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL) // one department can have many assigned employees
    private List<Assignment> assignments;


    //no argument constructor for Hibernate to create new instance of class
    public Department() {
    }


    //doesnt include ID as DB will generate automatically
    public Department(String name, Integer budget, String location) {
        this.name = name;
        this.budget = budget;
        this.location = location;
    }


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Integer getBudget() {return budget;}
    public void setBudget(Integer budget) {this.budget = budget;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public List<Assignment> getAssignments() {return assignments;}
    public void setAssignments(List<Assignment> assignments) {this.assignments = assignments;}
}
