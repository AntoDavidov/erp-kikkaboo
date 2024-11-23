package nl.fontys.s3.erp.business.EmployeeUseCases;

import nl.fontys.s3.erp.business.DTOs.EmployeeDTOs.UpdateEmployeeRequest;

public interface UpdateEmployeeUseCase {
    void updateEmployee(UpdateEmployeeRequest request);
}
