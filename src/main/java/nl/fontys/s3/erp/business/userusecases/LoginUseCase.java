package nl.fontys.s3.erp.business.userusecases;

import nl.fontys.s3.erp.business.dtos.userdto.LoginRequest;
import nl.fontys.s3.erp.business.dtos.userdto.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
