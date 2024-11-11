package nl.fontys.s3.erp.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AnnouncementCannotBeEditedBySpecialist extends ResponseStatusException {
    public AnnouncementCannotBeEditedBySpecialist() {
        super(HttpStatus.BAD_REQUEST, "Announcement cannot be edited by specialist");
    }
}
