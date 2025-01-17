package nl.fontys.s3.erp.business.userusecases;

import nl.fontys.s3.erp.domain.users.User;

public interface GetUserByEmployeeIdUseCase {
    User getUserByEmployeeId(long employeeId);
}
