package nl.fontys.s3.erp.business.announcementsusecases;

import nl.fontys.s3.erp.domain.announcements.Announcement;

public interface GetAnnouncementUseCase {
    Announcement getAnnouncement(long id);
}
