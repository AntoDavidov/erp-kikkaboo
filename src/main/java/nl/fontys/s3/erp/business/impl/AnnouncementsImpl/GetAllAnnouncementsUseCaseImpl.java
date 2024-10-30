package nl.fontys.s3.erp.business.impl.AnnouncementsImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.AnnouncementsUseCases.GetAllAnnouncementsUseCase;
import nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs.GetAnnouncementsResponse;
import nl.fontys.s3.erp.business.impl.converters.AnnouncementConverter;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllAnnouncementsUseCaseImpl implements GetAllAnnouncementsUseCase {
    private final AnnouncementRepository announcementRepository;

    @Override
    public GetAnnouncementsResponse getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAll()
                .stream()
                .map(AnnouncementConverter::convert)
                .toList();

        return GetAnnouncementsResponse.builder().announcements(announcements).build();
    }
}
