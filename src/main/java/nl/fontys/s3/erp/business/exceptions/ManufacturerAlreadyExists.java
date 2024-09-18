package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ManufacturerAlreadyExists extends ResponseStatusException {
    public ManufacturerAlreadyExists() {super(HttpStatus.BAD_REQUEST, "MANUFACTURER_ALREADY_EXISTS_BY_COMPANY_NAME");}
}
