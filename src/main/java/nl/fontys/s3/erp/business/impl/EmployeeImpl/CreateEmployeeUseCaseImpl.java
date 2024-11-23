package nl.fontys.s3.erp.business.impl.EmployeeImpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.CreateEmployeeRequest;
import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.CreateEmployeeResponse;
import nl.fontys.s3.erp.business.EmployeeUseCases.CreateEmployeeUseCase;
import nl.fontys.s3.erp.business.exceptions.EmployeeAlreadyExistsByCode;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateEmployeeUseCaseImpl implements CreateEmployeeUseCase {
    private final EmployeeRepository employeeRepository;

    @Override
    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest request) {
        if(employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new EmployeeAlreadyExistsByCode();
        }
        EmployeeEntity employeeEntity = saveNewEmployee(request);

        return CreateEmployeeResponse.builder()
                .id(employeeEntity.getId())
                .build();
    }

    private EmployeeEntity saveNewEmployee(CreateEmployeeRequest request) {
        EmployeeEntity newEmployee = EmployeeEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .department(request.getDepartment())
                .employeeCode(request.getEmployeeCode())
                .address(request.getAddress())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .status(request.getStatus())
                .salary(request.getSalary())
                .build();
        return employeeRepository.save(newEmployee);
    }
}
