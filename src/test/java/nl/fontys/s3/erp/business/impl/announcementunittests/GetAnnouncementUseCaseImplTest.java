package nl.fontys.s3.erp.business.impl.announcementunittests;

import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.business.impl.announcementsimpl.GetAnnouncementUseCaseImpl;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

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
