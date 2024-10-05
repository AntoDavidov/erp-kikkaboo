package nl.fontys.s3.erp.business.UserUseCases;

import nl.fontys.s3.erp.domain.users.User;

public interface GetUserUseCase {
    User getUser(long id);
}
