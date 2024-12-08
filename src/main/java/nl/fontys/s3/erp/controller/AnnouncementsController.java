package nl.fontys.s3.erp.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.announcementsusecases.*;
import nl.fontys.s3.erp.business.dtos.announcementdto.CreateAnnouncementRequest;
import nl.fontys.s3.erp.business.dtos.announcementdto.CreateAnnouncementResponse;
import nl.fontys.s3.erp.business.dtos.announcementdto.GetAnnouncementsResponse;
import nl.fontys.s3.erp.business.dtos.announcementdto.UpdateAnnouncementRequest;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcements")
@AllArgsConstructor
public class AnnouncementsController {
    private final CreateAnnouncementUseCase createAnnouncementUseCase;
    private final DeleteAnnouncementUseCase deleteAnnouncementUseCase;
    private final GetAnnouncementUseCase getAnnouncementUseCase;
    private final GetAllAnnouncementsUseCase getAllAnnouncementsUseCase;
    private final UpdateAnnouncementUseCase updateAnnouncementUseCase;

    @GetMapping()
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<GetAnnouncementsResponse> getAnnouncements() {
        return ResponseEntity.ok(getAllAnnouncementsUseCase.getAllAnnouncements());
    }

    @PostMapping()
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER')")
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(@RequestBody @Valid CreateAnnouncementRequest request) {
        CreateAnnouncementResponse response = createAnnouncementUseCase.createAnnouncement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER')")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable int id) {
        deleteAnnouncementUseCase.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER') or hasRole('SPECIALIST')")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable(value = "id") final long id) {
        final Announcement announcement = getAnnouncementUseCase.getAnnouncement(id);
        if(announcement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(announcement);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('CEO') or hasRole('MANAGER')")
    public ResponseEntity<Void> updateAnnouncement(@PathVariable(value = "id") final long id, @RequestBody @Valid UpdateAnnouncementRequest request) {
        request.setId(id);
        updateAnnouncementUseCase.updateAnnouncement(request);
        return ResponseEntity.noContent().build();
    }
}
