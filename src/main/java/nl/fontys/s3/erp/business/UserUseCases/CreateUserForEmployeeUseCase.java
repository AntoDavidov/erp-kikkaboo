package nl.fontys.s3.erp.business.UserUseCases;

import nl.fontys.s3.erp.business.DTOs.UserDTOs.CreateUserForEmployeeRequest;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.CreateUserForEmployeeResponse;

public interface CreateUserForEmployeeUseCase {
    CreateUserForEmployeeResponse createUserForEmployee(CreateUserForEmployeeRequest request);
}
