package nl.fontys.s3.erp.business.impl.EmployeeImpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.GetAllEmployeesResponse;
import nl.fontys.s3.erp.business.EmployeeUseCases.GetAllEmployeeUseCase;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.converters.EmployeeConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.users.Employee;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllEmployeesUseCaseImpl implements GetAllEmployeeUseCase {
    private final EmployeeRepository employeeRepository;
    private final AccessToken accessToken;

    @Override
    public GetAllEmployeesResponse getAllEmployees() {
        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("ACCOUNTING")) {
            throw new PermissionDenied("get all employees.");
        }
        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .map(EmployeeConverter::toEmployee)
                .toList();
        return GetAllEmployeesResponse.builder().employees(employees).build();
    }
}
