package nl.fontys.s3.erp.business.impl.AnnouncementsImpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.AnnouncementsUseCases.GetAnnouncementUseCase;
import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.business.impl.converters.AnnouncementConverter;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAnnouncementUseCaseImpl implements GetAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;

    @Override
    public Announcement getAnnouncement(long id) {
        AnnouncementEntity announcementEntity = announcementRepository.findById(id);
        if(announcementEntity == null){
            throw new AnnouncementDoesNotExist();
        }
        return AnnouncementConverter.convert(announcementEntity);
    }
}
