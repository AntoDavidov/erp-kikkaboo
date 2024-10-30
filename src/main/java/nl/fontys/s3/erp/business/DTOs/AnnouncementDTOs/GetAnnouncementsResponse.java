package nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.s3.erp.domain.announcements.Announcement;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetAnnouncementsResponse {
    private List<Announcement> announcements;
}
