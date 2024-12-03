package nl.fontys.s3.erp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.fontys.s3.erp.business.dtos.employeedto.CreateEmployeeRequest;
import nl.fontys.s3.erp.business.dtos.employeedto.CreateEmployeeResponse;
import nl.fontys.s3.erp.business.dtos.employeedto.GetAllEmployeesResponse;
import nl.fontys.s3.erp.business.employeeusecases.CreateEmployeeUseCase;
import nl.fontys.s3.erp.business.employeeusecases.GetAllEmployeeUseCase;
import nl.fontys.s3.erp.business.employeeusecases.GetEmployeeUseCase;
import nl.fontys.s3.erp.domain.users.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final GetAllEmployeeUseCase getAllEmployeeUseCase;
    private final GetEmployeeUseCase getEmployeeUseCase;

    @GetMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<Employee> getEmployee(@PathVariable(value = "id") final long id) {
        final Optional<Employee> employee = getEmployeeUseCase.getEmployee(id);
        if(employee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(employee.get());
    }

    @GetMapping
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<GetAllEmployeesResponse> getAllEmployees() {
        return ResponseEntity.ok(getAllEmployeeUseCase.getAllEmployees());
    }

    @PostMapping
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<CreateEmployeeResponse> createEmployee(@RequestBody @Valid CreateEmployeeRequest request) {
        CreateEmployeeResponse response = createEmployeeUseCase.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
