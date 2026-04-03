package com.example.api.repository;

import com.example.api.model.Employee;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by department name through Assignment
    @Query("SELECT a.employee FROM Assignment a WHERE a.department.name = :deptName")
    List<Employee> findByDepartmentName(@Param("deptName") String deptName);

    //find by id
    @Override
    Optional<Employee> findById(Long id);
    Optional<Employee> findByEmail(String email);


}