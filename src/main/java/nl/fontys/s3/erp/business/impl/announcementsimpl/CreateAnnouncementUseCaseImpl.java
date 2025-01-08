package nl.fontys.s3.erp.business.impl.announcementsimpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.announcementsusecases.CreateAnnouncementUseCase;
import nl.fontys.s3.erp.business.dtos.announcementdto.CreateAnnouncementRequest;
import nl.fontys.s3.erp.business.dtos.announcementdto.CreateAnnouncementResponse;
import nl.fontys.s3.erp.business.exceptions.AnnouncementCannotBeEditedBySpecialist;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

//ask how to get current user
@Service
@AllArgsConstructor
public class CreateAnnouncementUseCaseImpl implements CreateAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository; // Inject UserRepository


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
        UserEntity userEntity = userRepository.findByEmployeeId(request.getCreatedBy().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AnnouncementEntity newAnnouncementEntity = AnnouncementEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(request.getCreatedAt())
                .createdBy(userEntity)
                .department(request.getDepartment())
                .expirationDate(request.getExpirationDate())
                .type(request.getType())
                .isDepartmentOnly(request.isDepartmentOnly())
                .build();

        return announcementRepository.save(newAnnouncementEntity);
    }

}
