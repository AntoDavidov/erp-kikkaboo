package nl.fontys.s3.erp.business.employeeusecases;

import nl.fontys.s3.erp.business.dtos.employeedto.UpdateEmployeeRequest;

public interface UpdateEmployeeUseCase {
    void updateEmployee(UpdateEmployeeRequest request);
}
