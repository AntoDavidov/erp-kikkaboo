package nl.fontys.s3.erp.business.userusecases;

import nl.fontys.s3.erp.business.dtos.userdto.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}
