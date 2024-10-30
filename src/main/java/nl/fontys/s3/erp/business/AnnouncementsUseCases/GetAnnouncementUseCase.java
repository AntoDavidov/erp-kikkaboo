package nl.fontys.s3.erp.business.AnnouncementsUseCases;

import nl.fontys.s3.erp.domain.announcements.Announcement;

public interface GetAnnouncementUseCase {
    Announcement getAnnouncement(long id);
}
