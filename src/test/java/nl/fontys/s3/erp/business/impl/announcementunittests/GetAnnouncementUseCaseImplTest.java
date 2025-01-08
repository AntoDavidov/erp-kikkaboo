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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAnnouncementUseCaseImplTest {
    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private GetAnnouncementUseCaseImpl getAnnouncementUseCase;

    @Test
    void getAnnouncement_throwsAnnouncementDoesNotExist_whenAnnouncementNotFound() {
        // Arrange
        long announcementId = 1L;
        when(announcementRepository.findById(announcementId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AnnouncementDoesNotExist.class, () -> getAnnouncementUseCase.getAnnouncement(announcementId));

        // Verify: Interaction with repository
        verify(announcementRepository).findById(announcementId);
    }
}
