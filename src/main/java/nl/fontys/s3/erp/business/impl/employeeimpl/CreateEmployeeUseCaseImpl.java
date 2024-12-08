package nl.fontys.s3.erp.business.impl.employeeimpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.dtos.employeedto.CreateEmployeeRequest;
import nl.fontys.s3.erp.business.dtos.employeedto.CreateEmployeeResponse;
import nl.fontys.s3.erp.business.employeeusecases.CreateEmployeeUseCase;
import nl.fontys.s3.erp.business.exceptions.EmployeeAlreadyExistsByCode;
import nl.fontys.s3.erp.business.exceptions.PermissionDenied;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.users.Status;
import nl.fontys.s3.erp.persistence.DepartmentRepository;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import nl.fontys.s3.erp.persistence.entity.DepartmentEntity;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreateEmployeeUseCaseImpl implements CreateEmployeeUseCase {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AccessToken accessToken;

    @Override
    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest request) {
        if(accessToken.getDepartments() == null || !accessToken.getDepartments().contains("ACCOUNTING")) {
            throw new PermissionDenied("add an employee.");
        }
        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new EmployeeAlreadyExistsByCode();
        }

        EmployeeEntity employeeEntity = saveNewEmployee(request);

        return CreateEmployeeResponse.builder()
                .id(employeeEntity.getId())
                .build();
    }

    private EmployeeEntity saveNewEmployee(CreateEmployeeRequest request) {
        Set<DepartmentEntity> departmentEntities = request.getDepartments()
                .stream()
                .map(department -> departmentRepository.findByName(department.name()))
                .collect(Collectors.toSet());

        if (departmentEntities.contains(null)) {
            throw new IllegalArgumentException("One or more departments do not exist in the database");
        }

        EmployeeEntity newEmployee = EmployeeEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .departments(departmentEntities)
                .employeeCode(request.getEmployeeCode())
                .address(request.getAddress())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .status(Status.ACTIVE)
                .salary(request.getSalary())
                .build();

        return employeeRepository.save(newEmployee);
    }

}

