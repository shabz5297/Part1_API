Project overview: safedispatch hq administration API

The Part1_API repository is a Spring Boot application designed for managing company infrastructure, specifically employees, departments, and their inter-assignments. It utilizes Spring Security for role-based access control and JPA/Hibernate for persistence.
Core data models

The system architecture revolves around three primary entities located in Part1/API/src/main/java/com/example/api/model/:

    Employee: Tracks personal data, salary, start date, and system roles.
    Department: Tracks organizational units, locations, and budget constraints.
    Assignment: Acts as a join table with additional metadata (role and access level) to facilitate a many-to-many relationship between Employees and Departments.

```
erDiagram
    EMPLOYEE {
        Long id PK
        String name
        Double salary
        LocalDate startDate
    }
    DEPARTMENT {
        Long id PK
        String unitName
        Double budget
    }
    ASSIGNMENT {
        Long employee_id FK
        Long department_id FK
        String role
        Integer accessLevel
    }
    APP_USER {
        Long id PK
        String username
        String password
        Long employee_id FK
    }

    EMPLOYEE ||--o{ ASSIGNMENT : "assigned"
    DEPARTMENT ||--o{ ASSIGNMENT : "holds"
    APP_USER ||--|| EMPLOYEE : "linked to"
```
    
The diagram above shows the relational structure where Assignments link Employees to Departments.
API implementation

The REST interface is documented in Part1/API/src/main/resources/api-spec.yaml and implemented via controllers:
Controller	Endpoint	Method	Description
EmployeeController	/api/employees	GET	List all employees (supports ?department= filter).
EmployeeController	/api/employees	POST	Create new employee (HR only).
EmployeeController	/api/employees/{id}/promote	PUT	Increases salary by 10% if tenure > 6 months.
DepartmentController	/api/departments	GET	List all departments.
DepartmentController	/api/departments/{id}/employees	GET	List all employees in a specific department.
AssignmentController	/api/assignments	POST	Assign an employee to a department.
Security configuration

Security is managed in Part1/API/src/main/java/com/example/api/security/SecurityConfig.java using HTTP Basic Authentication and BCrypt password encoding.

    RBAC (Role-Based Access Control):
        ROLE_HR: Authorized for all GET, POST, PUT, and DELETE operations.
        ROLE_MANAGER: Authorized for GET operations only.
    Authentication: Uses a custom UserDetailsService that retrieves AppUser entities from the database.
    Initialization: DataLoader.java automatically populates the database with default users (hr/hr123 and manager/manager123) if the repository is empty.

Key business logic

The most significant business logic is the promotion workflow found in EmployeeService.java:

    Tenure Check: Validates that employee.getStartDate() is at least 6 months prior to the current date.
    Salary Adjustment: Applies a 1.1x multiplier to the current salary.
    Role Update: Prepends "Senior " to the employee's existing role string.

Error handling and validation

The project uses a GlobalExceptionHandler (Part1/API/src/main/java/com/example/api/exception/GlobalExceptionHandler.java) to provide consistent API responses:

    Validation: Uses jakarta.validation annotations (e.g., @Email, @Positive) on models.
    Exceptions: Maps IllegalArgumentException and ResponseStatusException to specific HTTP status codes (400, 404, etc.) with JSON error bodies.

Testing suite

Testing is implemented using JUnit 5 and Mockito, found in Part1/API/src/test/java/com/example/api/:

    EmployeeControllerTest.java: Mocks the repository to test promotion eligibility logic and controller response codes.
    DepartmentServiceTest.java: Validates that the service layer correctly rejects non-positive budgets for new departments.
