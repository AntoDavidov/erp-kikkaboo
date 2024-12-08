package nl.fontys.s3.erp.business.announcementsusecases;

import nl.fontys.s3.erp.business.dtos.announcementdto.UpdateAnnouncementRequest;

public interface UpdateAnnouncementUseCase {
    void updateAnnouncement(UpdateAnnouncementRequest request);
}
