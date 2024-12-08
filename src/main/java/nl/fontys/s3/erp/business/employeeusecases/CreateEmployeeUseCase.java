package nl.fontys.s3.erp.business.employeeusecases;

import nl.fontys.s3.erp.business.dtos.employeedto.CreateEmployeeRequest;
import nl.fontys.s3.erp.business.dtos.employeedto.CreateEmployeeResponse;

public interface CreateEmployeeUseCase {
    CreateEmployeeResponse createEmployee(CreateEmployeeRequest request);
}
