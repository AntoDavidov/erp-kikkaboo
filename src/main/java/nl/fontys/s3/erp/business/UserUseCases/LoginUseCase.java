package nl.fontys.s3.erp.business.UserUseCases;

import nl.fontys.s3.erp.business.DTOs.UserDTOs.LoginRequest;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
