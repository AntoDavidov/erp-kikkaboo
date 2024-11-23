package nl.fontys.s3.erp.business.impl.EmployeeImpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.EmployeeUseCases.GetEmployeeUseCase;
import nl.fontys.s3.erp.business.impl.converters.EmployeeConverter;
import nl.fontys.s3.erp.domain.users.Employee;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetEmployeeUseCaseImpl implements GetEmployeeUseCase {
    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> getEmployee(long id) {
        Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(id);
        return employeeEntityOptional.map(EmployeeConverter::toEmployee);
    }

}
