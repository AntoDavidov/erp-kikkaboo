package nl.fontys.s3.erp.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nl.fontys.s3.erp.domain.announcements.AnnouncementType;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class AnnouncementEntity {
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private User createdBy;

    private Department department;

    private LocalDateTime expirationDate;

    private AnnouncementType type;

    private boolean isDepartmentOnly;
}
