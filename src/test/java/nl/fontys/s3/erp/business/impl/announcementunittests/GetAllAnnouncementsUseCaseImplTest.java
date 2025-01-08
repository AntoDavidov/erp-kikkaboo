package nl.fontys.s3.erp.business.impl.announcementunittests;

import nl.fontys.s3.erp.business.dtos.announcementdto.GetAnnouncementsResponse;
import nl.fontys.s3.erp.business.impl.announcementsimpl.GetAllAnnouncementsUseCaseImpl;
import nl.fontys.s3.erp.business.impl.converters.AnnouncementConverter;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllAnnouncementsUseCaseImplTest {
    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private GetAllAnnouncementsUseCaseImpl getAllAnnouncementsUseCase;

    @Test
    void getAllAnnouncements_returnsEmptyList_whenNoAnnouncementsExist() {
        // Arrange: Simulate no announcements in the repository
        when(announcementRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        GetAnnouncementsResponse response = getAllAnnouncementsUseCase.getAllAnnouncements();

        // Assert
        assertEquals(0, response.getAnnouncements().size());
    }
    @Test
    void getAllAnnouncements_returnsListOfAnnouncements_whenAnnouncementsExist() {
        // Arrange: Create mock AnnouncementEntity and converted Announcement
        AnnouncementEntity mockEntity1 = AnnouncementEntity.builder()
                .id(1L)
                .title("Announcement 1")
                .content("Content 1")
                .build();

        AnnouncementEntity mockEntity2 = AnnouncementEntity.builder()
                .id(2L)
                .title("Announcement 2")
                .content("Content 2")
                .build();

        List<AnnouncementEntity> entities = List.of(mockEntity1, mockEntity2);

        when(announcementRepository.findAll()).thenReturn(entities);

        // Mock the conversion using AnnouncementConverter
        Announcement announcement1 = Announcement.builder()
                .id(1L)
                .title("Announcement 1")
                .content("Content 1")
                .build();

        Announcement announcement2 = Announcement.builder()
                .id(2L)
                .title("Announcement 2")
                .content("Content 2")
                .build();

        mockStatic(AnnouncementConverter.class);
        when(AnnouncementConverter.convert(mockEntity1)).thenReturn(announcement1);
        when(AnnouncementConverter.convert(mockEntity2)).thenReturn(announcement2);

        // Act
        GetAnnouncementsResponse response = getAllAnnouncementsUseCase.getAllAnnouncements();

        // Assert
        assertEquals(2, response.getAnnouncements().size());
        assertEquals("Announcement 1", response.getAnnouncements().get(0).getTitle());
        assertEquals("Announcement 2", response.getAnnouncements().get(1).getTitle());
    }
}
