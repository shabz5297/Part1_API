package com.example.api.service;

import com.example.api.model.Department;
import com.example.api.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void createDepartment_shouldThrow_ifBudgetIsNotPositive() {
        Department department = new Department();
        department.setName("Logistics");
        department.setBudget(-1000);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                departmentService.createDepartment(department));

        assertEquals("Budget must be positive", exception.getMessage());
    }

    @Test
    void createDepartment_shouldSave_ifBudgetIsPositive() {
        Department department = new Department();
        department.setName("Logistics");
        department.setBudget(10000);
        department.setLocation("Leicester");

        when(departmentRepository.save(department)).thenReturn(department);

        Department savedDepartment = departmentService.createDepartment(department);

        assertEquals("Logistics", savedDepartment.getName());
        assertEquals(10000, savedDepartment.getBudget());
    }
}