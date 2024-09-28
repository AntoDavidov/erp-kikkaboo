package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductDoesNotExist extends ResponseStatusException {
    public ProductDoesNotExist() {
        super(HttpStatus.NOT_FOUND, "Product does not exist");
    }
}
