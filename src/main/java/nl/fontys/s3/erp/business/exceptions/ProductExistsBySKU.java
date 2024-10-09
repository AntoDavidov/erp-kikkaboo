package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ProductExistsBySKU extends ResponseStatusException {
    public ProductExistsBySKU() {
        super(HttpStatus.BAD_REQUEST, "Product with the same SKU already exists");
    }
}
