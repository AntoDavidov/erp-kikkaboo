package nl.fontys.s3.erp.business.EmployeeUseCases;

import nl.fontys.s3.erp.domain.users.Employee;

import java.util.Optional;

public interface GetEmployeeUseCase {
    Optional<Employee> getEmployee(long id);
}
