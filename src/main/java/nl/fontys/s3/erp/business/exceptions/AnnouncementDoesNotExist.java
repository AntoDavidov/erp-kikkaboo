package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AnnouncementDoesNotExist extends ResponseStatusException {
    public AnnouncementDoesNotExist() {
        super(HttpStatus.NOT_FOUND, "Announcement does not exist");
    }
}
