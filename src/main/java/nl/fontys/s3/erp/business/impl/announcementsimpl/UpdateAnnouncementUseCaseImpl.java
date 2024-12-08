package nl.fontys.s3.erp.business.impl.announcementsimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.announcementsusecases.UpdateAnnouncementUseCase;
import nl.fontys.s3.erp.business.dtos.announcementdto.UpdateAnnouncementRequest;
import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.business.impl.converters.UserConverter;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateAnnouncementUseCaseImpl implements UpdateAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;

    @Override
    public void updateAnnouncement(UpdateAnnouncementRequest request) {
        Optional<AnnouncementEntity> announcementEntity = announcementRepository.findById(request.getId());

        if(announcementEntity.isEmpty()) {
            throw new AnnouncementDoesNotExist();
        }
        AnnouncementEntity announcement = announcementEntity.get();
        updateAnnouncement(request, announcement);
        announcementRepository.save(announcement);

    }

    private void updateAnnouncement(UpdateAnnouncementRequest request, AnnouncementEntity entity) {
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setCreatedAt(request.getCreatedAt());
        entity.setCreatedBy(UserConverter.convert(request.getCreatedBy()));
        entity.setDepartment(request.getDepartment());
        entity.setExpirationDate(request.getExpirationDate());
        entity.setType(request.getType());
    }
}
