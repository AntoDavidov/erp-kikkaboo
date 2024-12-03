package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ManufacturerDoesNotExist extends ResponseStatusException {
    public ManufacturerDoesNotExist() { super (HttpStatus.BAD_REQUEST, "MANUFACTURER_DOES_NOT_EXIST");}
}
