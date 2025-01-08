package nl.fontys.s3.erp.business.impl.announcementunittests;

import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.business.impl.announcementsimpl.GetAnnouncementUseCaseImpl;
import nl.fontys.s3.erp.business.impl.converters.AnnouncementConverter;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAnnouncementUseCaseImplTest {
    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private GetAnnouncementUseCaseImpl getAnnouncementUseCase;

    @Test
    void getAnnouncement_throwsException_whenAnnouncementDoesNotExist() {
        // Arrange
        long announcementId = 1L;
        when(announcementRepository.findById(announcementId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AnnouncementDoesNotExist.class,
                () -> getAnnouncementUseCase.getAnnouncement(announcementId));
    }
    @Test
    void getAnnouncement_returnsAnnouncement_whenAnnouncementExists() {
        // Arrange
        long announcementId = 1L;
        DepartmentEntity accountingDepartment = DepartmentEntity.builder()
                .id(1L)
                .name("ACCOUNTING")
                .build();

        UserEntity ceoUserEntity = UserEntity.builder()
                .id(4L)
                .email("ceo@test.com")
                .role(UserRoleEntity.builder()
                        .id(1L)
                        .role(Role.CEO)
                        .build())
                .employee(EmployeeEntity.builder()
                        .id(1L)
                        .employeeCode("EMP001")
                        .firstName("John")
                        .lastName("Doe")
                        .departments(Set.of(accountingDepartment))
                        .address("123 Street")
                        .phone("1234567890")
                        .dateOfBirth(LocalDate.of(1990, 1, 1)).build())
                .build();

        // Create a mock AnnouncementEntity
        AnnouncementEntity mockEntity = AnnouncementEntity.builder()
                .id(announcementId)
                .title("Test Announcement")
                .content("This is a test announcement.")
                .createdBy(ceoUserEntity)
                .build();

        // Directly use the converter to create the expected domain Announcement
        Announcement expectedAnnouncement = AnnouncementConverter.convert(mockEntity);

        // Simulate repository behavior
        when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(mockEntity));

        // Act
        Announcement result = getAnnouncementUseCase.getAnnouncement(announcementId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAnnouncement.getId(), result.getId());
        assertEquals(expectedAnnouncement.getTitle(), result.getTitle());
        assertEquals(expectedAnnouncement.getContent(), result.getContent());
    }
}
