package com.example.api.repository;

import com.example.api.model.Department;
import com.example.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Get all employees in a department by id
    @Query("SELECT a.employee FROM Assignment a WHERE a.department.id = :deptId")
    List<Employee> findEmployeesByDepartmentId(@Param("deptId") Long deptId);

}