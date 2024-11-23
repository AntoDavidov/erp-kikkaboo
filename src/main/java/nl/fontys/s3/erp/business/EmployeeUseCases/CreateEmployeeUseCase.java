package nl.fontys.s3.erp.business.EmployeeUseCases;

import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.CreateEmployeeRequest;
import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.CreateEmployeeResponse;

public interface CreateEmployeeUseCase {
    CreateEmployeeResponse createEmployee(CreateEmployeeRequest request);
}
