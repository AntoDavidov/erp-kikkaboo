package nl.fontys.s3.erp.business.impl.announcementunittests;

import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.business.impl.announcementsimpl.GetAnnouncementUseCaseImpl;
import nl.fontys.s3.erp.business.impl.converters.AnnouncementConverter;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

        AnnouncementEntity mockEntity = AnnouncementEntity.builder()
                .id(announcementId)
                .title("Test Announcement")
                .content("This is a test announcement.")
                .build();

        Announcement expectedAnnouncement = Announcement.builder()
                .id(announcementId)
                .title("Test Announcement")
                .content("This is a test announcement.")
                .build();

        when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(mockEntity));

        // Mock the static method AnnouncementConverter.convert
        mockStatic(AnnouncementConverter.class);
        when(AnnouncementConverter.convert(mockEntity)).thenReturn(expectedAnnouncement);

        // Act
        Announcement result = getAnnouncementUseCase.getAnnouncement(announcementId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAnnouncement.getId(), result.getId());
        assertEquals(expectedAnnouncement.getTitle(), result.getTitle());
        assertEquals(expectedAnnouncement.getContent(), result.getContent());
    }
}
