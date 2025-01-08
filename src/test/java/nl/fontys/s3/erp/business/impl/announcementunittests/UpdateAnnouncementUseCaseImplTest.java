package nl.fontys.s3.erp.business.impl.announcementunittests;

import nl.fontys.s3.erp.business.dtos.announcementdto.UpdateAnnouncementRequest;
import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.business.impl.announcementsimpl.UpdateAnnouncementUseCaseImpl;
import nl.fontys.s3.erp.business.impl.converters.UserConverter;
import nl.fontys.s3.erp.domain.announcements.AnnouncementType;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateAnnouncementUseCaseImplTest {
    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private UpdateAnnouncementUseCaseImpl updateAnnouncementUseCase;

    @Test
    void updateAnnouncement_throwsException_whenAnnouncementDoesNotExist() {
        // Arrange
        UpdateAnnouncementRequest request = UpdateAnnouncementRequest.builder()
                .id(1L)
                .title("Updated Announcement")
                .build();

        when(announcementRepository.findById(request.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AnnouncementDoesNotExist.class,
                () -> updateAnnouncementUseCase.updateAnnouncement(request));
    }
    @Test
    void updateAnnouncement_updatesSuccessfully_whenAnnouncementExists() {
        // Arrange
        long announcementId = 1L;

        User requestUser = User.builder()
                .id(1L)
                .email("user@test.com")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .email("user@test.com")
                .build();

        UpdateAnnouncementRequest request = UpdateAnnouncementRequest.builder()
                .id(announcementId)
                .title("Updated Announcement")
                .content("Updated content")
                .createdAt(new Date())
                .createdBy(requestUser)
                .department(Department.ACCOUNTING)
                .expirationDate(new Date(System.currentTimeMillis() + 86400000)) // +1 day
                .type(AnnouncementType.GENERAL)
                .build();

        AnnouncementEntity existingAnnouncement = AnnouncementEntity.builder()
                .id(announcementId)
                .title("Old Announcement")
                .content("Old content")
                .build();

        when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(existingAnnouncement));

        mockStatic(UserConverter.class);
        when(UserConverter.convert(requestUser)).thenReturn(userEntity);

        // Act
        updateAnnouncementUseCase.updateAnnouncement(request);

        // Assert
        assertEquals("Updated Announcement", existingAnnouncement.getTitle());
        assertEquals("Updated content", existingAnnouncement.getContent());
        assertEquals(request.getCreatedAt(), existingAnnouncement.getCreatedAt());
        assertEquals(userEntity, existingAnnouncement.getCreatedBy());
        assertEquals(request.getDepartment(), existingAnnouncement.getDepartment());
        assertEquals(request.getExpirationDate(), existingAnnouncement.getExpirationDate());
        assertEquals(request.getType(), existingAnnouncement.getType());
    }
}
