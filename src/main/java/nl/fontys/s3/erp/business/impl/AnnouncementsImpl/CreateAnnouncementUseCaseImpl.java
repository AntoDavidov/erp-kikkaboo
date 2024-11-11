package nl.fontys.s3.erp.business.impl.AnnouncementsImpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.AnnouncementsUseCases.CreateAnnouncementUseCase;
import nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs.CreateAnnouncementRequest;
import nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs.CreateAnnouncementResponse;
import nl.fontys.s3.erp.business.exceptions.AnnouncementCannotBeEditedBySpecialist;
import nl.fontys.s3.erp.business.impl.converters.UserConverter;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import org.springframework.stereotype.Service;

//ask how to get current user
@Service
@AllArgsConstructor
public class CreateAnnouncementUseCaseImpl implements CreateAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;

    @Override
    public CreateAnnouncementResponse createAnnouncement(CreateAnnouncementRequest request) {
        if(request.getCreatedBy().getRole() == Role.SPECIALIST){
            throw new AnnouncementCannotBeEditedBySpecialist();
        }

        AnnouncementEntity announcementEntity = saveNewAnnouncement(request);

        return CreateAnnouncementResponse.builder()
                .id(announcementEntity.getId())
                .build();
    }

    private AnnouncementEntity saveNewAnnouncement(CreateAnnouncementRequest request) {
        AnnouncementEntity newAnnouncementEntity = AnnouncementEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(request.getCreatedAt())
                .createdBy(UserConverter.convert(request.getCreatedBy()))
                .department(request.getDepartment())
                .expirationDate(request.getExpirationDate())
                .type(request.getType())
                .isDepartmentOnly(request.isDepartmentOnly())
                .build();

        return announcementRepository.save(newAnnouncementEntity);
    }

}
