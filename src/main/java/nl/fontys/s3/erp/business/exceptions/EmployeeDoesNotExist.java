package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmployeeDoesNotExist extends ResponseStatusException {
    public EmployeeDoesNotExist() {
        super(HttpStatus.NOT_FOUND, "Employee not found.");
    }
}
