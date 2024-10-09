package nl.fontys.s3.erp.business.AnnouncementsUseCases;

import nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs.UpdateAnnouncementRequest;
import nl.fontys.s3.erp.domain.announcements.Announcement;

public interface UpdateAnnouncementUseCase {
    void updateAnnouncement(UpdateAnnouncementRequest request);
}
