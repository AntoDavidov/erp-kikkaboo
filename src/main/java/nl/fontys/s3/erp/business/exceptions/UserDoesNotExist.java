package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserDoesNotExist extends ResponseStatusException {
  public UserDoesNotExist() {
    super(HttpStatus.NOT_FOUND, "Product does not exist");
  }
}
