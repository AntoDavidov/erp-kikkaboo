package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAccountAlreadyExistsForEmployee extends ResponseStatusException {
    public UserAccountAlreadyExistsForEmployee() {
        super(HttpStatus.BAD_REQUEST, "An user account already exists for this employee.");
    }
}
