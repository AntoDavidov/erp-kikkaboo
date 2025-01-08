package nl.fontys.s3.erp.business.impl.announcementunittests;

import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.business.impl.announcementsimpl.DeleteAnnouncementUseCaseImpl;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAnnouncementUseCaseImplTest {
    @Mock
    private AnnouncementRepository announcementRepository;

    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private DeleteAnnouncementUseCaseImpl deleteAnnouncementUseCase;
    @Test
    void deleteAnnouncement_throwsException_whenAnnouncementDoesNotExist() {
        // Arrange
        long announcementId = 1L;
        when(announcementRepository.existsById(announcementId)).thenReturn(false);

        // Act & Assert
        assertThrows(AnnouncementDoesNotExist.class,
                () -> deleteAnnouncementUseCase.deleteAnnouncement(announcementId));

        // Verify: Only existence check is performed, no deletion
        verify(announcementRepository).existsById(announcementId);
        verifyNoMoreInteractions(announcementRepository);
    }
    @Test
    void deleteAnnouncement_deletesSuccessfully_whenAnnouncementExists() {
        // Arrange
        long announcementId = 1L;
        when(announcementRepository.existsById(announcementId)).thenReturn(true);

        // Act
        deleteAnnouncementUseCase.deleteAnnouncement(announcementId);

        // Assert: No exceptions thrown

        // Verify: Both existence check and delete operations are performed
        verify(announcementRepository).existsById(announcementId);
        verify(announcementRepository).deleteById(announcementId);
        verifyNoInteractions(accessToken); // Ensure no interaction with AccessToken
    }
}
