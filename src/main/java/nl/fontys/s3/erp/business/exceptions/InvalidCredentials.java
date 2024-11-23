package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCredentials extends ResponseStatusException {
    public InvalidCredentials() {
        super(HttpStatus.BAD_REQUEST, "Invalid credentials. Please try again.");
    }
}
