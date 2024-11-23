package nl.fontys.s3.erp.business.impl.EmployeeImpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.GetAllEmployeesResponse;
import nl.fontys.s3.erp.business.EmployeeUseCases.GetAllEmployeeUseCase;
import nl.fontys.s3.erp.business.impl.converters.EmployeeConverter;
import nl.fontys.s3.erp.domain.users.Employee;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllEmployeesUseCaseImpl implements GetAllEmployeeUseCase {
    private final EmployeeRepository employeeRepository;

    @Override
    public GetAllEmployeesResponse getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .map(EmployeeConverter::toEmployee)
                .toList();
        return GetAllEmployeesResponse.builder().employees(employees).build();
    }
}
