package nl.fontys.s3.erp.business.announcementsusecases;

import nl.fontys.s3.erp.business.dtos.announcementdto.CreateAnnouncementRequest;
import nl.fontys.s3.erp.business.dtos.announcementdto.CreateAnnouncementResponse;

public interface CreateAnnouncementUseCase {
    CreateAnnouncementResponse createAnnouncement(CreateAnnouncementRequest request);
}
