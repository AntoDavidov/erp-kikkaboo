package nl.fontys.s3.erp.business.AnnouncementsUseCases;

import nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs.CreateAnnouncementRequest;
import nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs.CreateAnnouncementResponse;

public interface CreateAnnouncementUseCase {
    CreateAnnouncementResponse createAnnouncement(CreateAnnouncementRequest request);
}
