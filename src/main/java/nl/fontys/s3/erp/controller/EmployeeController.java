package nl.fontys.s3.erp.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.CreateEmployeeRequest;
import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.CreateEmployeeResponse;
import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.GetAllEmployeesResponse;
import nl.fontys.s3.erp.business.EmployeeUseCases.CreateEmployeeUseCase;
import nl.fontys.s3.erp.business.EmployeeUseCases.GetAllEmployeeUseCase;
import nl.fontys.s3.erp.business.EmployeeUseCases.GetEmployeeUseCase;
import nl.fontys.s3.erp.business.EmployeeUseCases.UpdateEmployeeUseCase;
import nl.fontys.s3.erp.domain.users.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//    @RolesAllowed({"MANAGER", "CEO"})
    public ResponseEntity<Employee> getEmployee(@PathVariable(value = "id") final long id) {
        final Optional<Employee> employee = getEmployeeUseCase.getEmployee(id);
        if(employee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(employee.get());
    }

    @GetMapping
//    @RolesAllowed({"CEO", "MANAGER"})
    public ResponseEntity<GetAllEmployeesResponse> getAllEmployees() {
        return ResponseEntity.ok(getAllEmployeeUseCase.getAllEmployees());
    }

    @PostMapping
//    @RolesAllowed({"CEO"})
    public ResponseEntity<CreateEmployeeResponse> createEmployee(@RequestBody @Valid CreateEmployeeRequest request) {
        CreateEmployeeResponse response = createEmployeeUseCase.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
