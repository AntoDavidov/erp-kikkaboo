package nl.fontys.s3.erp.business.impl.AnnouncementsImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.AnnouncementsUseCases.DeleteAnnouncementUseCase;
import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.UserDoesNotExist;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAnnouncementUseCaseImpl implements DeleteAnnouncementUseCase {
    public final AnnouncementRepository announcementRepository;

    @Override
    public void deleteAnnouncement(long id) {
        if(!announcementRepository.existsById(id)){
            throw new AnnouncementDoesNotExist();
        }
        announcementRepository.deleteById(id);
    }
}
