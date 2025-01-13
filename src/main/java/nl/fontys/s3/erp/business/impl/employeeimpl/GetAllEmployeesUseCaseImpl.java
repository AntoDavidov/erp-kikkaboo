package nl.fontys.s3.erp.business.impl.employeeimpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.dtos.employeedto.GetAllEmployeesResponse;
import nl.fontys.s3.erp.business.employeeusecases.GetAllEmployeeUseCase;
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

        List<Long> employeeIdsWithUserAccounts = employeeRepository.findEmployeeIdsWithUserAccounts();

        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .map(employeeEntity -> {
                    Employee employee = EmployeeConverter.toEmployee(employeeEntity);
                    employee.setHasUserAccount(employeeIdsWithUserAccounts.contains(employee.getId()));
                    return employee;
                })
                .toList();
        return GetAllEmployeesResponse.builder().employees(employees).build();
    }
}
