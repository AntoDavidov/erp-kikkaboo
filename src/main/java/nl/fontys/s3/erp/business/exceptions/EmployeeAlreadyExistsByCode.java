package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmployeeAlreadyExistsByCode extends ResponseStatusException {
    public EmployeeAlreadyExistsByCode() {super(HttpStatus.BAD_REQUEST, "Employee already exists by employee code");}
}
