package nl.fontys.s3.erp.business.userusecases;

import nl.fontys.s3.erp.business.dtos.userdto.CreateUserForEmployeeRequest;
import nl.fontys.s3.erp.business.dtos.userdto.CreateUserForEmployeeResponse;

public interface CreateUserForEmployeeUseCase {
    CreateUserForEmployeeResponse createUserForEmployee(CreateUserForEmployeeRequest request);
}
