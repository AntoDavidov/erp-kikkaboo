package nl.fontys.s3.erp.business.impl.employeeimpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.employeeusecases.GetEmployeeUseCase;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.business.impl.converters.EmployeeConverter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.users.Employee;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetEmployeeUseCaseImpl implements GetEmployeeUseCase {
    private final EmployeeRepository employeeRepository;
    private final AccessToken accessToken;

    @Override
    public Optional<Employee> getEmployee(long id) {
        // Allow self-access
        if (accessToken.getEmployeeId() == id) {
            Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(id);
            return employeeEntityOptional.map(EmployeeConverter::toEmployee);
        }

        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("ACCOUNTING")) {
            throw new PermissionDenied("get an employee.");
        }
        Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(id);
        return employeeEntityOptional.map(EmployeeConverter::toEmployee);
    }

}
