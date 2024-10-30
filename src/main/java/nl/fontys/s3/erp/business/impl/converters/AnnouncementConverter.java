package nl.fontys.s3.erp.business.impl.converters;

import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;

public class AnnouncementConverter {
    private AnnouncementConverter() {

    }
    public static Announcement convert(AnnouncementEntity entity) {
        return Announcement.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .department(entity.getDepartment())
                .expirationDate(entity.getExpirationDate())
                .type(entity.getType())
                .isDepartmentOnly(entity.isDepartmentOnly())
                .build();
    }
}
