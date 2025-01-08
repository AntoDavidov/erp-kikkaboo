package nl.fontys.s3.erp.business.impl.announcementunittests;

import nl.fontys.s3.erp.business.dtos.announcementdto.CreateAnnouncementRequest;
import nl.fontys.s3.erp.business.dtos.announcementdto.CreateAnnouncementResponse;
import nl.fontys.s3.erp.business.exceptions.AnnouncementCannotBeEditedBySpecialist;
import nl.fontys.s3.erp.business.impl.announcementsimpl.CreateAnnouncementUseCaseImpl;
import nl.fontys.s3.erp.domain.announcements.AnnouncementType;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAnnouncementUseCaseImplTest {
    @Mock
    private AnnouncementRepository announcementRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateAnnouncementUseCaseImpl createAnnouncementUseCase;

    @Test
    void createAnnouncement_throwsException_whenCreatedBySpecialist() {
        // Arrange: Create a domain User with Role.SPECIALIST
        User specialistUser = User.builder()
                .id(1L)
                .role(Role.SPECIALIST) // Directly using the Role enum
                .build();

        CreateAnnouncementRequest request = CreateAnnouncementRequest.builder()
                .createdBy(specialistUser) // Provide the domain User with SPECIALIST role
                .build();

        // Act & Assert
        assertThrows(AnnouncementCannotBeEditedBySpecialist.class,
                () -> createAnnouncementUseCase.createAnnouncement(request));

        // Verify: No repository interactions should occur
        verifyNoInteractions(announcementRepository, userRepository);
    }
    @Test
    void createAnnouncement_throwsIllegalArgumentException_whenUserNotFound() {
        // Arrange: User with Role.MANAGER (valid role)
        User managerUser = User.builder()
                .id(2L)
                .role(Role.MANAGER)
                .build();

        CreateAnnouncementRequest request = CreateAnnouncementRequest.builder()
                .createdBy(managerUser)
                .title("Important Announcement")
                .build();

        // Simulate user not found in the database
        when(userRepository.findByEmployeeId(2L)).thenReturn(Optional.empty());

        // Act & Assert: Expect IllegalArgumentException when user is not found
        assertThrows(IllegalArgumentException.class,
                () -> createAnnouncementUseCase.createAnnouncement(request));

        // Verify: Only userRepository lookup should be called
        verify(userRepository).findByEmployeeId(2L);
        verifyNoInteractions(announcementRepository);
    }
    @Test
    void createAnnouncement_savesSuccessfully_whenCreatedByManager() {
        // Arrange: Create a domain User with Role.MANAGER
        User managerUser = User.builder()
                .id(1L)
                .role(Role.MANAGER) // Using Role.MANAGER to allow creation
                .build();

        when(userRepository.findByEmployeeId(1L)).thenReturn(Optional.of(mock(UserEntity.class)));

        AnnouncementEntity savedAnnouncement = AnnouncementEntity.builder()
                .id(1L)
                .title("Important Announcement")
                .build();

        when(announcementRepository.save(any(AnnouncementEntity.class))).thenReturn(savedAnnouncement);

        CreateAnnouncementRequest request = CreateAnnouncementRequest.builder()
                .title("New Announcement")
                .content("Details about the announcement")
                .createdAt(new Date())
                .createdBy(managerUser)
                .department(Department.ACCOUNTING)
                .expirationDate(new Date(System.currentTimeMillis() + 86400000)) // +1 day
                .type(AnnouncementType.GENERAL)
                .isDepartmentOnly(false)
                .build();

        // Act
        CreateAnnouncementResponse response = createAnnouncementUseCase.createAnnouncement(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());

        // Verify: UserRepository and AnnouncementRepository interactions
        verify(userRepository).findByEmployeeId(1L);
        verify(announcementRepository).save(any(AnnouncementEntity.class));
    }
    @Test
    void createAnnouncement_savesSuccessfully_whenCreatedByCEO() {
        // Arrange: User with Role.CEO (valid role)
        User ceoUser = User.builder()
                .id(4L)
                .role(Role.CEO)
                .build();

        UserEntity ceoUserEntity = UserEntity.builder()
                .id(4L)
                .email("ceo@test.com")
                .build();

        AnnouncementEntity savedAnnouncement = AnnouncementEntity.builder()
                .id(2L)
                .title("CEO Announcement")
                .build();

        when(userRepository.findByEmployeeId(4L)).thenReturn(Optional.of(ceoUserEntity));
        when(announcementRepository.save(any(AnnouncementEntity.class))).thenReturn(savedAnnouncement);

        CreateAnnouncementRequest request = CreateAnnouncementRequest.builder()
                .title("CEO Announcement")
                .content("This is a special CEO announcement.")
                .createdAt(new Date())
                .createdBy(ceoUser)
                .department(Department.ACCOUNTING)
                .expirationDate(new Date(System.currentTimeMillis() + 172800000)) // +2 days
                .type(AnnouncementType.URGENT)
                .isDepartmentOnly(true)
                .build();

        // Act
        CreateAnnouncementResponse response = createAnnouncementUseCase.createAnnouncement(request);

        // Assert
        assertNotNull(response);
        assertEquals(2L, response.getId());

        // Verify: Both repositories are interacted with correctly
        verify(userRepository).findByEmployeeId(4L);
        verify(announcementRepository).save(any(AnnouncementEntity.class));
    }
}
